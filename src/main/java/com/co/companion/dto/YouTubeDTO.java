package com.co.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YouTubeDTO {
    private String part;
    private String chart;
    private String regionCode;
    private int maxResults;
    private int videoCategoryId;
    private boolean videoSyndicated;
    private String type;
    private String q;
    private String pageToken;
}
