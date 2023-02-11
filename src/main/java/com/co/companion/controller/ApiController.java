package com.co.companion.controller;

import com.co.companion.dto.ResponseDTO;
import com.co.companion.dto.YouTubeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
            URI uri = UriComponentsBuilder
                    .fromUriString(YOUTUBE_URL)
                    .path("/" + type)
                    .queryParam("part", youTubeDTO.getPart())
                    .queryParam("chart", youTubeDTO.getChart())
                    .queryParam("regionCode", youTubeDTO.getRegionCode())
                    .queryParam("maxResults", youTubeDTO.getMaxResults())
                    .queryParam("videoCategoryId", youTubeDTO.getVideoCategoryId())
                    .queryParam("key", YOUTUBE_KEY)
                    .encode()
                    .build()
                    .toUri();


            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            log.info("status code : {}", response.getStatusCode());
            log.info("body : {}", response.getBody());

            return ResponseEntity.ok().body(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("유튜브 데이터 가져오기 실패");

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
