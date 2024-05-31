package com.jibi.back_end.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.jibi.back_end.dto.CreancierDto;
import com.jibi.back_end.models.Creancier;

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
