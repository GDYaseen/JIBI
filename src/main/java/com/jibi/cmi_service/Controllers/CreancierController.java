package com.jibi.cmi_service.Controllers;

import com.jibi.cmi_service.dto.CreancierDto;
import com.jibi.cmi_service.mapper.CreancierMapper;
import com.jibi.cmi_service.services.CreancierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
