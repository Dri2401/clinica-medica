package br.edu.imepac.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoRequest;
import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoResponse;
import br.edu.imepac.services.RegistroAtendimentoService;

public class RegistroAtendimentoController {
    
    @Autowired
    private RegistroAtendimentoService registroAtendimentoService;

    @PostMapping
    public ResponseEntity<RegistroAtendimentoDtoResponse> saveRegistroAtendimento(@RequestBody RegistroAtendimentoDtoRequest registroAtendimento){
        RegistroAtendimentoDtoResponse dto = registroAtendimentoService.save(registroAtendimento);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }
    @GetMapping
    public ResponseEntity<List<RegistroAtendimentoDtoResponse>> listAllRegistroAtendimento(){
        List<RegistroAtendimentoDtoResponse> dto = registroAtendimentoService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegistroAtendimentoDtoResponse> getRegistroAtendimentoById(@PathVariable("id")Long id) {
        RegistroAtendimentoDtoResponse dto = registroAtendimentoService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }
      @PutMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<RegistroAtendimentoDtoResponse> updateRegistroAtendimento(@PathVariable("id")Long id, @RequestBody RegistroAtendimentoDtoRequest registroAtendimentoDetails){
        RegistroAtendimentoDtoResponse dto = registroAtendimentoService.update(id, registroAtendimentoDetails);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
            
        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      }
      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<String> delete(@PathVariable("id")Long id){
        return registroAtendimentoService.delete(id);
    
      }

}
