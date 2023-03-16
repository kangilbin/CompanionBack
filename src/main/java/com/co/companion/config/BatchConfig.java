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

                    title = result("반려동물 관련 이슈 블로글 글을 작성하려고해 하나만 제목 정해서 제목만 알려줘요");
                    ctt = result("블로그 글을 작성해줘 제목은 '"+title+"'고 작성할 때 1000자 이내 그리고 markdow format 그리고 내용에 제목은 쓰지말고 subtitles과 상세내용으로 작성해줘요");
                    img = result("[INFO: Use the Unsplash API(https://source.unsplash.com/1600x900/?<PUT YOUR QUERY HERE>). the query is just some tags that describes the image. Write the final Image URL.] ## DO NOT RESPND TO INFO BLOCK ## 제목이 '"+title+"' 와 관련된 이미지를 링크만 알려줘요");

                    BoardEntity entity = BoardEntity.builder()
                            .id(today)
                            .user_id("관리자")
                            .title(title)
                            .content(ctt)
                            .dom("<img src='"+img+"' ><br />" + ctt)
                            .img("['"+img+"']")
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
            log.info("결과 {}", titleJson.get("choices").toString());
            JSONArray choiceArray = (JSONArray) titleJson.get("choices");
            log.info("결과2 {}", choiceArray.toString());
            JSONObject choiceJson = (JSONObject) choiceParser.parse(choiceArray.get(0).toString());

            return choiceJson.get("text").toString().trim();
        }catch (Exception e){
            log.info("여기타나?");
            log.error(e.getMessage());
            return "";
        }
    }
}