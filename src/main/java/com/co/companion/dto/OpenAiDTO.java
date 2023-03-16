package com.co.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiDTO {
    private String prompt;
    private int maxTokens;
    private double temperature;
    private int n;
    private Map<String, String> model;

    public OpenAiDTO(String prompt, int maxTokens, double temperature, int n, String modelId) {
        this.prompt = prompt;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.n = n;

        this.model = new HashMap<>();
        this.model.put("model", modelId);
    }
}
