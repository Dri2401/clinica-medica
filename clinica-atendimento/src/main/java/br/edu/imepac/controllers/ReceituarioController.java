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

import br.edu.imepac.dtos.Receituario.ReceituarioDtoRequest;
import br.edu.imepac.dtos.Receituario.ReceituarioDtoResponse;
import br.edu.imepac.services.ReceituarioService;

@Controller
@RequestMapping("receituario")
public class ReceituarioController {
    @Autowired
    private ReceituarioService receituarioService;

    @PostMapping
    public ResponseEntity<ReceituarioDtoResponse> saveReceituario(@RequestBody ReceituarioDtoRequest receituario){
        ReceituarioDtoResponse dto = receituarioService.save(receituario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }
    @GetMapping
    public ResponseEntity<List<ReceituarioDtoResponse>> listAllReceituario(){
        List<ReceituarioDtoResponse> dto = receituarioService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReceituarioDtoResponse> getReceituarioById(@PathVariable("id")Long id) {
        ReceituarioDtoResponse dto = receituarioService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }
      @PutMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<ReceituarioDtoResponse> updateReceituario(@PathVariable("id")Long id, @RequestBody ReceituarioDtoRequest receituarioDetails){
        ReceituarioDtoResponse dto = receituarioService.update(id, receituarioDetails);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
            
        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      }
      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<String> delete(@PathVariable("id")Long id){
        return receituarioService.delete(id);
    
      }

    
}
