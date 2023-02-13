package com.co.companion.controller;

import com.co.companion.dto.AnimalDTO;
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
    @Value("${NAVER-ID}")
    private String NAVER_ID;
    @Value("${NAVER-PW}")
    private String NAVER_PW;
    @Value("${NAVER-URL}")
    private String NAVER_URL;
    @Value("${ANIMAL-URL}")
    private String ANIMAL_URL;
    @Value("${ANIMAL-KEY}")
    private String ANIMAL_KEY;


    // 유튜브 데이터 가져오기
    @PostMapping("/youtube/{type}")
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
                            .queryParam("q", youTubeDTO.getQ())
                            .queryParam("type", youTubeDTO.getType())
                            .queryParam("key", YOUTUBE_KEY)
                            .build())
                    .retrieve().toEntity(String.class).block();

            log.info("유튜브 데이터 가져오기 성공");
            log.info("status : {}", response.getStatusCode());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유튜브 데이터 가져오기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 네이버 뉴스 데이터 가져오기
    @GetMapping("/news")
    public ResponseEntity<?> newsList(@RequestParam(required = false) String query, @RequestParam(required = false) String sort, @RequestParam(required = false) int display) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(NAVER_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", query)
                            .queryParam("sort", sort)
                            .queryParam("display", display)
                            .queryParam("key", YOUTUBE_KEY)
                            .build())
                    .headers(
                            httpHeaders -> {
                                httpHeaders.set("X-Naver-Client-Id", NAVER_ID);
                                httpHeaders.set("X-Naver-Client-Secret", NAVER_PW);
                            })
                    .retrieve().toEntity(String.class).block();

            log.info("뉴스 데이터 가자오기 성공");
            log.info("status : {}", response.getStatusCode());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("뉴스 테이터 가져오기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/animal/abandonmentPublic")
    public ResponseEntity<?> animalList(@RequestBody AnimalDTO animalDTO) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(ANIMAL_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/abandonmentPublic")
                            .queryParam("numOfRows", animalDTO.getNumOfRows())
                            .queryParam("_type", animalDTO.get_type())
                            .queryParam("bgnde", animalDTO.getBgnde())
                            .queryParam("endde", animalDTO.getEndde())
                            .queryParam("upkind", animalDTO.getUpkind())
                            .queryParam("upr_cd", animalDTO.getUpr_cd())
                            .queryParam("org_cd", animalDTO.getOrg_cd())
                            .queryParam("neuter_yn", animalDTO.getNeuter_yn())
                            .queryParam("pageNo", animalDTO.getPageNo())
                            .queryParam("serviceKey", ANIMAL_KEY)
                            .build())
                    .retrieve().toEntity(String.class).block();


            log.info("유기동물 데이터 가져오기 성공");
            log.info("status : {}", response.getStatusCode());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유기동물 테이터 가져오기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/animal/sido")
    public ResponseEntity<?> animalSido(@RequestParam(required = false) int numOfRows, @RequestParam(required = false) String _type) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(ANIMAL_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/sido")
                            .queryParam("numOfRows", numOfRows)
                            .queryParam("_type", _type)
                            .queryParam("serviceKey", ANIMAL_KEY)
                            .build())
                    .retrieve().toEntity(String.class).block();


            log.info("유기동물 시도 가져오기 성공");
            log.info("status : {}", response.getStatusCode());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유기동물 시도 가져오기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/animal/sigungu")
    public ResponseEntity<?> animalSigungu(@RequestParam(required = false) String upr_cd, @RequestParam(required = false) String _type) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(ANIMAL_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/sigungu")
                            .queryParam("upr_cd", upr_cd)
                            .queryParam("_type", _type)
                            .queryParam("serviceKey", ANIMAL_KEY)
                            .build())
                    .retrieve().toEntity(String.class).block();


            log.info("유기동물 시군구 가져오기 성공");
            log.info("status : {}", response.getStatusCode());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유기동물 시군구 가져오기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
