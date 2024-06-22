package br.edu.imepac.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.imepac.dtos.Exames.ExameDtoRequest;
import br.edu.imepac.dtos.Exames.ExamesDtoResponse;
import br.edu.imepac.services.ExamesService;

@Controller
@RequestMapping("exames")
public class ExamesController {
    @Autowired
    private ExamesService examesService;

    @PostMapping
    public ResponseEntity<ExamesDtoResponse> saveExames(@RequestBody ExameDtoRequest exames){
        ExamesDtoResponse dto = examesService.save(exames);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }
    @GetMapping
    public ResponseEntity<List<ExamesDtoResponse>> listAllExames(){
        List<ExamesDtoResponse> dto = examesService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamesDtoResponse> getExamesById(@PathVariable("id")Long id) {
        ExamesDtoResponse dto = examesService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }
      @PutMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<ExamesDtoResponse> updateExames(@PathVariable("id")Long id, @RequestBody ExameDtoRequest examesDetails){
        ExamesDtoResponse dto = examesService.update(id, examesDetails);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
            
        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      }
      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<String> delete(@PathVariable("id")Long id){
        return examesService.delete(id);
    
      }

}
