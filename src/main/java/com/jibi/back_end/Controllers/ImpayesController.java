package com.jibi.back_end.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.services.ImpayeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/impaye")
@AllArgsConstructor
public class ImpayesController {
    private ImpayeService impayeService;

    @GetMapping
    public ResponseEntity<List<Impaye>> getImpayes(@RequestParam Long userId,@RequestParam Long creanceId){
        List<Impaye> list = impayeService.getImpayesByClientIdAndImpayeId(userId, creanceId);
        return new ResponseEntity<List<Impaye>>(list,HttpStatus.OK);
    }
}
