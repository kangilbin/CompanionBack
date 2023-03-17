package com.co.companion.config;

import com.co.companion.model.BoardEntity;
import com.co.companion.model.UserEntity;
import com.co.companion.service.BoardService;
import com.co.companion.service.OpenAIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${OPEN_AI_KEY}")
    private String OPEN_KEY;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private DocumentRepository documentRepository;
//
    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private BoardService boardAIService;

    @Bean
    public Job job() {

        Job job = jobBuilderFactory.get("openAi")
                .start(step())
                .build();

        return job;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("배치 실행");
                    String title = "";
                    String ctt = "";
                    String img = "";
                    String today =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                    title = result("I'm going to write an issue related to pets in Korean, so please choose a title and let me know.");
                    ctt = result("Please write a blog post in Korean, the title is '"+title+"'  Don't write a title in the form of a markdown, just write a subtitle and details. Please don't let it go over 500 characters.");
                    img = result("[INFO: Use the Unsplash API(https://source.unsplash.com/1600x900/?<PUT YOUR QUERY HERE>). the query is just some tags that describes the image. Write the final Image URL.] ## DO NOT RESPND TO INFO BLOCK ## Give me a blog cover image url fit to this subject '"+title+"'");
                    BoardEntity entity = BoardEntity.builder()
                            .id(today)
                            .user_id("관리자")
                            .title(title)
                            .content(ctt)
                            .dom("<img src='"+img+"' ><br /><pre>" + ctt + "</pre>")
                            .img("["+'"'+img+'"'+"]")
                            .build();
                    boardAIService.create(entity);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    public String result(String str) {
        try {
            JSONParser parser = new JSONParser();
            JSONParser choiceParser = new JSONParser();
            JSONObject titleJson = (JSONObject) parser.parse(openAIService.getCompletion(str));
            JSONArray choiceArray = (JSONArray) titleJson.get("choices");
            JSONObject choiceJson = (JSONObject) choiceParser.parse(choiceArray.get(0).toString());
            log.info("결과 {}", choiceJson.get("text").toString().trim());

            return choiceJson.get("text").toString().trim();
        }catch (Exception e){
            log.info("여기타나?");
            log.error(e.getMessage());
            return "";
        }
    }
}