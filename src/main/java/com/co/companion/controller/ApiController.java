package com.co.companion.controller;

import com.co.companion.dto.YouTubeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {

    @Value("${YOUTUBE-URL}")
    private String YOUTUBE_URL;
    @Value("${YOUTUBE-KEY}")
    private String YOUTUBE_KEY;


    // 유튜브 데이터 가져오기
    @GetMapping("/youtube/{type}")
    public ResponseEntity<?> youTubeList(@PathVariable(required = false) String type, @RequestBody YouTubeDTO youTubeDTO) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(YOUTUBE_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + type)
                            .queryParam("part", youTubeDTO.getPart())
                            .queryParam("chart", youTubeDTO.getChart())
                            .queryParam("regionCode", youTubeDTO.getRegionCode())
                            .queryParam("maxResults", youTubeDTO.getMaxResults())
                            .queryParam("videoCategoryId", youTubeDTO.getVideoCategoryId())
                            .queryParam("key", YOUTUBE_KEY)
                            .build())
                    .retrieve().toEntity(String.class).block();


            log.info("status : {}", response.getStatusCode());
            log.info("body : {}", response.getBody());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유튜브 데이터 가져오기 실패");

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
