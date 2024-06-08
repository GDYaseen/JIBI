package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.dto.CreancierDto;
import com.jibi.back_end.mapper.CreancierMapper;
import com.jibi.back_end.models.Creance;
import com.jibi.back_end.models.Creancier;
import com.jibi.back_end.models.User;
import com.jibi.back_end.request.CreancierRequest;
import com.jibi.back_end.services.CreancierService;
import com.jibi.back_end.services.CreanceService;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cmi/creancier")
@AllArgsConstructor
public class CreancierController {

    private CreancierService creancierService;
    private CreanceService creanceService;

    @GetMapping
    public ResponseEntity<List<CreancierDto>> listCreanciers(){
        // System.out.println(SecurityContextHolder.getContext().getAuthentication().);
        List<CreancierDto> list = CreancierMapper.creancierListToCreancierDtoList(creancierService.getAllCreanciers());
        return new ResponseEntity<List<CreancierDto>>(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCreancier(@RequestBody CreancierRequest request){
        Creancier cr = creancierService.getCreancierByCreancierCode(request.getCreancierCode());
        if(cr!=null) return new ResponseEntity<>("Creancier already exists",HttpStatus.BAD_REQUEST);
    
        cr = Creancier.builder()
            .category(request.getCategory())
            .creancierCode(request.getCreancierCode())
            .creancierName(request.getCreancierName())
            .build();
        cr = creancierService.saveCreancier(cr);
        return new ResponseEntity<Creancier>(cr,HttpStatus.OK);
        
    }
    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifyCreancier(@RequestBody CreancierRequest request,@PathVariable Long id){
        Creancier cr = creancierService.getCreancierById(id);
        if(cr==null) return new ResponseEntity<>("Creancier doens't exist",HttpStatus.NOT_FOUND);
    
        // List<Creance> creanceList = creanceService.getAllCreancesByIds(Arrays.asList(request.getCreances()));

        // for(Creance creance : creanceList){
        //     creance.setCreancier(cr);
        // }
        // creanceService.saveAllCreances(creanceList);
        // System.out.println(request.getCreances());
            cr.setCategory(request.getCategory());
            cr.setCreancierName(request.getCreancierName());
            // cr.setCreances(creanceList);

            cr = creancierService.saveCreancier(cr);
        return new ResponseEntity<Creancier>(cr,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreancier(@PathVariable Long id ){
        Creancier cr = creancierService.getCreancierById(id);
        if(cr==null) return new ResponseEntity<>("Creancier doens't exist",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(cr);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCreancier(@PathVariable Long id){
        creancierService.deleteCreancier(id);
        return ResponseEntity.ok("Creancier Deleted");
    }
}
