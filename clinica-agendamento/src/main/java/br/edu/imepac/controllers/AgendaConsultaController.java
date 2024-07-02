package br.edu.imepac.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoRequest;
import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoResponse;
import br.edu.imepac.services.AgendaConsultaService;

public class AgendaConsultaController {
    @Autowired
    private AgendaConsultaService agendaConsultaService;

    private static final Logger logger = LoggerFactory.getLogger(AgendaConsultaController.class);

    @PostMapping
    public ResponseEntity<AgendaConsultaDtoResponse> saveagendaConsulta(@RequestBody AgendaConsultaDtoRequest agendaConsultaDetails){
        logger.info("agendaConsulta Controller create agendaConsulta");
        AgendaConsultaDtoResponse dto = agendaConsultaService.save(agendaConsultaDetails);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgendaConsultaDtoResponse>> findAll(){
        List<AgendaConsultaDtoResponse> dto = agendaConsultaService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaConsultaDtoResponse> findById(@PathVariable("id") Long id){
        AgendaConsultaDtoResponse dto = agendaConsultaService.findById(id);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaConsultaDtoResponse> update(@PathVariable("id") Long id, @RequestBody AgendaConsultaDtoRequest agendaConsultaDetails){
        AgendaConsultaDtoResponse dto = agendaConsultaService.update(id, agendaConsultaDetails);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return agendaConsultaService.delete(id);
    }




}
