package com.jibi.cmi_service.mapper;


import com.jibi.cmi_service.dto.FormulaireDto;
import com.jibi.cmi_service.models.Formulaire;

public class FormulaireMapper {

    public static FormulaireDto formulaireToFormulaireDto(Formulaire f){
        return FormulaireDto.builder()
                .id(f.getId())
                .formData(f.getFormData())
                .build();
    }
}
