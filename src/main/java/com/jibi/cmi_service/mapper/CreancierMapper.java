package com.jibi.cmi_service.mapper;

import com.jibi.cmi_service.dto.CreancierDto;
import com.jibi.cmi_service.models.Creancier;

import java.util.List;
import java.util.stream.Collectors;

public class CreancierMapper {

    public static List<CreancierDto> creancierListToCreancierDtoList(List<Creancier> list){
        return list.stream().map(c -> CreancierDto.builder()
                .creancierCode(c.getCreancierCode())
                .creancierName(c.getCreancierName())
                .category(c.getCategory())
                .creances(CreanceMapper.creanceListToCreanceDtoList(c.getCreances()))
                .build()
        ).collect(Collectors.toList());
    }
}
