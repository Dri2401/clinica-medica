package br.edu.imepac.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.edu.imepac.dtos.Retorno.RetornoDtoRequest;
import br.edu.imepac.dtos.Retorno.RetornoDtoResponse;
import br.edu.imepac.services.RetornoServices;

@Controller
@RequestMapping("retorno")
public class RetornoController {
    @Autowired
    private RetornoServices retornoService;

    private static final Logger logger = LoggerFactory.getLogger(RetornoController.class);

    @PostMapping
    public ResponseEntity<RetornoDtoResponse> saveretorno(@RequestBody RetornoDtoRequest retornoDetails){
        logger.info("retorno Controller create retorno");
        RetornoDtoResponse dto = retornoService.save(retornoDetails);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RetornoDtoResponse>> findAll(){
        List<RetornoDtoResponse> dto = retornoService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetornoDtoResponse> findById(@PathVariable("id") Long id){
        RetornoDtoResponse dto = retornoService.findById(id);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetornoDtoResponse> update(@PathVariable("id") Long id, @RequestBody RetornoDtoRequest retornoDetails){
        RetornoDtoResponse dto = retornoService.update(id, retornoDetails);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return retornoService.delete(id);
    }
}
