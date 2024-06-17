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

import br.edu.imepac.dtos.paciente.PacienteDtoRequest;
import br.edu.imepac.dtos.paciente.PacienteDtoResponse;
import br.edu.imepac.services.PacienteService;

@Controller
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);

    @PostMapping
    public ResponseEntity<PacienteDtoResponse> savePaciente(@RequestBody PacienteDtoRequest pacienteDetails){
        logger.info("Paciente Controller create Paciente");
        PacienteDtoResponse dto = pacienteService.save(pacienteDetails);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PacienteDtoResponse>> findAll(){
        List<PacienteDtoResponse> dto = pacienteService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDtoResponse> findById(@PathVariable("id") Long id){
        PacienteDtoResponse dto = pacienteService.findById(id);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDtoResponse> update(@PathVariable("id") Long id, @RequestBody PacienteDtoRequest pacienteDetails){
        PacienteDtoResponse dto = pacienteService.update(id, pacienteDetails);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return pacienteService.delete(id);
    }


}
