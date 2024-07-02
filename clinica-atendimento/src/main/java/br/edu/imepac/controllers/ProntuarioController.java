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

import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.services.ProntuarioService;

@Controller
@RequestMapping("prontuario")
public class ProntuarioController {
    @Autowired
    private ProntuarioService prontuarioService;

    @PostMapping
    public ResponseEntity<ProntuarioDtoResponse> saveProntuario(@RequestBody ProntuarioDtoRequest prontuario){
        ProntuarioDtoResponse dto = prontuarioService.save(prontuario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }
    @GetMapping
    public ResponseEntity<List<ProntuarioDtoResponse>> listAllProntuario(){
        List<ProntuarioDtoResponse> dto = prontuarioService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProntuarioDtoResponse> getProntuarioById(@PathVariable("id")Long id) {
        ProntuarioDtoResponse dto = prontuarioService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }
      @PutMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<ProntuarioDtoResponse> updateProntuario(@PathVariable("id")Long id, @RequestBody ProntuarioDtoRequest prontuarioDetails){
        ProntuarioDtoResponse dto = prontuarioService.update(id, prontuarioDetails);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
            
        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      }
      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<String> delete(@PathVariable("id")Long id){
        return prontuarioService.delete(id);
    
      }

    
}
