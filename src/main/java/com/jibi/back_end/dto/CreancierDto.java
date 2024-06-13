package com.jibi.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreancierDto {
    private Long creanceId;
    private String creancierCode;
    private String creancierName;
    private String category;
    private List<CreanceDto> creances;
}
