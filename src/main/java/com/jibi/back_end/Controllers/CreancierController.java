package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.dto.CreancierDto;
import com.jibi.back_end.mapper.CreancierMapper;
import com.jibi.back_end.services.CreancierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cmi/creancier")
@AllArgsConstructor
public class CreancierController {

    private CreancierService creancierService;

    @GetMapping
    public ResponseEntity<List<CreancierDto>> listCreanciers(){
        List<CreancierDto> list = CreancierMapper.creancierListToCreancierDtoList(creancierService.getAllCreanciers());
        return new ResponseEntity<List<CreancierDto>>(list, HttpStatus.OK);
    }
}
