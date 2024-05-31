package com.jibi.back_end.mapper;


import com.jibi.back_end.dto.FormulaireDto;
import com.jibi.back_end.models.Formulaire;

public class FormulaireMapper {

    public static FormulaireDto formulaireToFormulaireDto(Formulaire f){
        return FormulaireDto.builder()
                .id(f.getId())
                .formData(f.getFormData())
                .build();
    }
}
