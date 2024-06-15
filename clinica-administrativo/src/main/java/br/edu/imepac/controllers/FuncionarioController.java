package br.edu.imepac.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.imepac.dtos.Funcionarios.FuncionarioDtoRequest;
import br.edu.imepac.dtos.Funcionarios.FuncionarioDtoResponse;
import br.edu.imepac.services.FuncionarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;



@Controller
@RequestMapping("funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);

    @PostMapping
    public ResponseEntity<FuncionarioDtoResponse> saveFuncionario(@RequestBody FuncionarioDtoRequest funcionario){
        logger.info("Funcionario Create");
        FuncionarioDtoResponse savedFuncionario = funcionarioService.save(funcionario);
        return new ResponseEntity<>(savedFuncionario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDtoResponse>> listAllFuncionarios(){
        logger.info("List All Funcionarios");
        List<FuncionarioDtoResponse> funcionarios = funcionarioService.findAll();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FuncionarioDtoResponse> findById(@PathVariable("id") Long id) {
        logger.info("Find by Id Funcionario");
        FuncionarioDtoResponse funcionario = funcionarioService.findById(id);
        if(funcionario != null){
            return new ResponseEntity<>(funcionario, HttpStatus.OK);
        } else{
            logger.info("Funcionario not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FuncionarioDtoResponse> updateFuncionario(@PathVariable("id") Long id, @RequestBody FuncionarioDtoRequest funcionarioDetails){
        logger.info("Update Funcionario");
        FuncionarioDtoResponse dto = funcionarioService.update(id, funcionarioDetails);
        if(dto != null){
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else{
            logger.info("Update Funcionario Error");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteFuncionario(@PathVariable("id") Long id) {
        logger.info("Delete funcionario ");
        return funcionarioService.delete(id);
    }
    
    
}
