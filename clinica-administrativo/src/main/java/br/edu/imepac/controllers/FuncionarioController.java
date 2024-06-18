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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;



@Controller
@RequestMapping("funcionario")
@Tag(name = "Funcionario-API")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);

    @Operation(summary = "Realiza o cadastro de Funcionários", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro do funcionário realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<FuncionarioDtoResponse> saveFuncionario(@RequestBody FuncionarioDtoRequest funcionario){
        logger.info("Funcionario Create");
        FuncionarioDtoResponse savedFuncionario = funcionarioService.save(funcionario);
        return new ResponseEntity<>(savedFuncionario, HttpStatus.CREATED);
    }

    @Operation(summary = "Realiza a busca de todos os funcionarios", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<FuncionarioDtoResponse>> listAllFuncionarios(){
        logger.info("List All Funcionarios");
        List<FuncionarioDtoResponse> funcionarios = funcionarioService.findAll();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de um funcionário pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
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

    @Operation(summary = "Atualiza os dados de um funcionário", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
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

    @Operation(summary = "Exclusão de um funcionário", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário deletado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro intenro")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteFuncionario(@PathVariable("id") Long id) {
        logger.info("Delete funcionario ");
        return funcionarioService.delete(id);
    }
    
    
}
