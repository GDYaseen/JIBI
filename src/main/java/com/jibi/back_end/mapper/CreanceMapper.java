package com.jibi.back_end.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.jibi.back_end.dto.CreanceDto;
import com.jibi.back_end.models.Creance;

public class CreanceMapper {
    public static List<CreanceDto> creanceListToCreanceDtoList(List<Creance> list){
        return list.stream().map(c -> CreanceDto.builder()
                .creanceCode(c.getCreanceCode())
                .creanceName(c.getCreanceName())
                .build()
        ).collect(Collectors.toList());
    }
}
