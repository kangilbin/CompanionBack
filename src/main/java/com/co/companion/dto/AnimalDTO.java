package com.co.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private int numOfRows;
    private String _type;
    private String bgnde;
    private String endde;
    private String upkind;
    private String upr_cd;
    private String org_cd;
    private String neuter_yn;
    private int pageNo;
}
