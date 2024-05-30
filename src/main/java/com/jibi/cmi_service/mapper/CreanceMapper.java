package com.jibi.cmi_service.mapper;

import com.jibi.cmi_service.dto.CreanceDto;
import com.jibi.cmi_service.models.Creance;

import java.util.List;
import java.util.stream.Collectors;

public class CreanceMapper {
    public static List<CreanceDto> creanceListToCreanceDtoList(List<Creance> list){
        return list.stream().map(c -> CreanceDto.builder()
                .creanceCode(c.getCreanceCode())
                .creanceName(c.getCreanceName())
                .build()
        ).collect(Collectors.toList());
    }
}
