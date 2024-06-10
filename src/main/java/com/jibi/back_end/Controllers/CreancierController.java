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
import com.jibi.back_end.models.Formulaire;
import com.jibi.back_end.request.CreanceRequest;
import com.jibi.back_end.request.CreancierRequest;
import com.jibi.back_end.services.CreancierService;
import com.jibi.back_end.services.CreanceService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/cmi/creancier")
@AllArgsConstructor
public class CreancierController {

    private CreancierService creancierService;
    private CreanceService creanceService;

    @GetMapping
    public ResponseEntity<List<CreancierDto>> listCreanciers(){
        List<CreancierDto> list = CreancierMapper.creancierListToCreancierDtoList(creancierService.getAllCreanciers());
        return new ResponseEntity<List<CreancierDto>>(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCreancier(@RequestBody CreancierRequest request){
        Creancier cr = creancierService.getCreancierByCreancierCode(request.getCreancierCode());
        if(cr!=null) return new ResponseEntity<>("{\"message\":\"Creancier already exists\"},",HttpStatus.BAD_REQUEST);
    
        List<Creance> listCreance = new ArrayList<Creance>();
        for(CreanceRequest c : request.getCreances()){
            Formulaire f = Formulaire.builder()
                .formData(c.getFormData())
                .creance(null)
                .build();

                Creance c_=Creance.builder().creanceCode(c.getCreanceCode())
                .creanceName(c.getCreanceName())
                .creancier(cr)
                .form(f)
                .build();
                f.setCreance(c_);

            listCreance.add(c_);
        }
        cr = Creancier.builder()
            .category(request.getCategory())
            .creancierCode(request.getCreancierCode())
            .creancierName(request.getCreancierName())
            .creances(listCreance)
            .build();
            
            
            for(Creance c:listCreance){
                c.setCreancier(cr);
                }
                cr = creancierService.saveCreancier(cr);
                
        // creanceService.saveAllCreances(listCreance);
        return new ResponseEntity<Creancier>(cr,HttpStatus.OK);
        
    }
    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifyCreancier(@RequestBody CreancierRequest request,@PathVariable Long id){
        Creancier cr = creancierService.getCreancierById(id);
        if(cr==null) return new ResponseEntity<>("{\"message\":\"Creancier Not found\"}",HttpStatus.NOT_FOUND);
    
        // List<Creance> listCreance = new ArrayList<Creance>();
        // for(CreanceRequest c : request.getCreances()){
        //     Formulaire f = Formulaire.builder()
        //         .formData(c.getFormData())
        //         .creance(null)
        //         .build();

        //         Creance c_=Creance.builder().creanceCode(c.getCreanceCode())
        //         .creanceName(c.getCreanceName())
        //         .creancier(cr)
        //         .form(f)
        //         .build();
        //         f.setCreance(c_);

        //     listCreance.add(c_);
        // }

            cr.setCategory(request.getCategory());
            cr.setCreancierName(request.getCreancierName());
            // cr.setCreances(creanceList);

            cr = creancierService.saveCreancier(cr);
        return new ResponseEntity<Creancier>(cr,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreancier(@PathVariable Long id ){
        Creancier cr = creancierService.getCreancierById(id);
        if(cr==null) return new ResponseEntity<>("{\"message\":\"Creancier doens't exist\"}",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(cr);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCreancier(@PathVariable Long id){
        creancierService.deleteCreancier(id);
        return ResponseEntity.ok("{\"message\":\"Creancier Deleted\"}");
    }


    /////////////////////////methodes ajoute/////////////////////////////////////

    @GetMapping("/code/{creancierCode}")
    public ResponseEntity<?> getCreancierByCode(@PathVariable String creancierCode) {
        Creancier cr = creancierService.getCreancierByCreancierCode(creancierCode);
        if (cr == null) return new ResponseEntity<>( "{\"message\":\"Creancier not found\"}",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(cr);
    }


    @DeleteMapping("/code/{creancierCode}")
    public ResponseEntity<Map<String, String>> deleteCreancierByCode(@PathVariable String creancierCode) {
        Creancier cr = creancierService.getCreancierByCreancierCode(creancierCode);
        if (cr == null) return new ResponseEntity<>(Map.of("message", "Creancier doesn't exist"), HttpStatus.NOT_FOUND);
        creancierService.deleteCreancier(cr.getId());
        return ResponseEntity.ok(Map.of("message", "Creancier Deleted"));
    }

    @PutMapping("/code/{creancierCode}/modify")
    public ResponseEntity<?> modifyCreancierByCode(@RequestBody CreancierRequest request, @PathVariable String creancierCode) {
        Creancier cr = creancierService.getCreancierByCreancierCode(creancierCode);
        if (cr == null) return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        cr.setCategory(request.getCategory());
        cr.setCreancierName(request.getCreancierName());

        cr = creancierService.saveCreancier(cr);
        return new ResponseEntity<Creancier>(cr, HttpStatus.OK);
    }

    @GetMapping("/code/{creancierCode}/getId")
    public ResponseEntity<?> getCreancierIdByCode(@PathVariable String creancierCode) {
        Creancier cr = creancierService.getCreancierByCreancierCode(creancierCode);
        if (cr == null) return new ResponseEntity<>("{\"message\":\"Creancier doesn't exist\"}", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(cr.getId());
    }
    @PutMapping("/{id}/addCreance")
    public ResponseEntity<?> addCreanceToCreancier(@PathVariable Long id, @RequestBody CreanceRequest creanceRequest) {
        Creancier cr = creancierService.getCreancierById(id);
        if (cr == null) return new ResponseEntity<>("{\"message\":\"Creancier doesn't exist\"}", HttpStatus.NOT_FOUND);

        Formulaire f = Formulaire.builder()
                .formData(creanceRequest.getFormData())
                .creance(null)
                .build();

        Creance c = Creance.builder()
                .creanceCode(creanceRequest.getCreanceCode())
                .creanceName(creanceRequest.getCreanceName())
                .creancier(cr)
                .form(f)
                .build();
        f.setCreance(c);
        cr.getCreances().add(c);
        creancierService.saveCreancier(cr);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }



    @DeleteMapping("/code/{creancierCode}/removeCreance/{creanceCode}")
    public ResponseEntity<String> removeCreanceByCreancierCode(@PathVariable String creancierCode, @PathVariable String creanceCode) {
        Creancier cr = creancierService.getCreancierByCreancierCode(creancierCode);
        if (cr == null) return new ResponseEntity<>( "{\"message\":\"Creancier doesn't exist\"}",HttpStatus.NOT_FOUND);

        Creance creanceToRemove = null;
        for (Creance c : cr.getCreances()) {
            if (c.getCreanceCode().equals(creanceCode)) {
                creanceToRemove = c;
                break;
            }
        }

        if (creanceToRemove != null) {
            // Remove associated Formulaire first
            creanceToRemove.getForm().setCreance(null);
            cr.getCreances().remove(creanceToRemove);
            creanceService.deleteCreance(creanceToRemove); // Make sure to implement this method in CreanceService
            creancierService.saveCreancier(cr);
            return new ResponseEntity<>("{\"message\":\"Creanceier Deleted\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{creancierId}/removeCreance/{creanceId}")
    public ResponseEntity<String> removeCreanceFromCreancier(@PathVariable Long creancierId, @PathVariable Long creanceId) {
        Creancier cr = creancierService.getCreancierById(creancierId);
        if (cr == null) return new ResponseEntity<>( "{\"message\":\"Creancier doesn't exist\"}",HttpStatus.NOT_FOUND);

        Creance creanceToRemove = null;
        for (Creance c : cr.getCreances()) {
            if (c.getId().equals(creanceId)) {
                creanceToRemove = c;
                break;
            }
        }

        if (creanceToRemove != null) {
            cr.getCreances().remove(creanceToRemove);
            creancierService.saveCreancier(cr);
            return new ResponseEntity<>("{\"message\":\"Creanceier Deleted\"}",HttpStatus.OK);
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{creancierId}/updateCreance/{creanceId}")
    public ResponseEntity<?> updateCreance(@PathVariable Long creancierId, @PathVariable Long creanceId, @RequestBody CreanceRequest creanceRequest) {
        Creancier cr = creancierService.getCreancierById(creancierId);
        if (cr == null) return new ResponseEntity<>("{\"message\":\"Creancier doesn't exist\"}", HttpStatus.NOT_FOUND);

        for (Creance c : cr.getCreances()) {
            if (c.getId().equals(creanceId)) {
                c.setCreanceCode(creanceRequest.getCreanceCode());
                c.setCreanceName(creanceRequest.getCreanceName());
                c.getForm().setFormData(creanceRequest.getFormData());
                creancierService.saveCreancier(cr);
                return new ResponseEntity<>(c, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("{\"message\":\"Creance doesn't exist\"}", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{creancierId}/creance/{creanceId}")
    public ResponseEntity<?> getCreanceById(@PathVariable Long creancierId, @PathVariable Long creanceId) {
        Creancier cr = creancierService.getCreancierById(creancierId);
        if (cr == null) return new ResponseEntity<>("{\"message\":\"Creancier doesn't exist\"}", HttpStatus.NOT_FOUND);

        for (Creance c : cr.getCreances()) {
            if (c.getId().equals(creanceId)) {
                return new ResponseEntity<>(c, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("{\"message\":\"Creance doesn't exist\"}", HttpStatus.NOT_FOUND);
    }
    //////////////////////////////////new methodes ////////////////////////////////////////////////////////


}
