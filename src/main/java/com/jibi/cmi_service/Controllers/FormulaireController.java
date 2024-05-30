package com.jibi.cmi_service.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.cmi_service.dto.FormulaireDto;
import com.jibi.cmi_service.mapper.FormulaireMapper;
import com.jibi.cmi_service.models.Formulaire;
import com.jibi.cmi_service.services.FormulaireService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/form")
@AllArgsConstructor
public class FormulaireController {
    
    private FormulaireService formulaireService;

    @GetMapping("")
    public ResponseEntity<FormulaireDto> getForms(@RequestParam Long id){
        Formulaire f = formulaireService.getFormulaireByCreanceId(id);
        if(f ==  null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    
        return new ResponseEntity<FormulaireDto>(FormulaireMapper.formulaireToFormulaireDto(f),HttpStatus.OK);
    }

}
