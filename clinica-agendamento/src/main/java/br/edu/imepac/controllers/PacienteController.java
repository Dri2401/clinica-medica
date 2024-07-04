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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("pacientes")
@Tag(name = "Paciente-API")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    @Operation(summary = "Realiza o cadastro de entidades", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro de entidade realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<PacienteDtoResponse> savePaciente(@RequestBody PacienteDtoRequest pacienteDetails){
        logger.info("Paciente Controller create Paciente");
        PacienteDtoResponse dto = pacienteService.save(pacienteDetails);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos as entidades cadastradas!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<PacienteDtoResponse>> findAll(){
        List<PacienteDtoResponse> dto = pacienteService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de entidade pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Entidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDtoResponse> findById(@PathVariable("id") Long id){
        PacienteDtoResponse dto = pacienteService.findById(id);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza os dados de uma entidade", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidade atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Entidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDtoResponse> update(@PathVariable("id") Long id, @RequestBody PacienteDtoRequest pacienteDetails){
        PacienteDtoResponse dto = pacienteService.update(id, pacienteDetails);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Operation(summary = "Exclusão de uma entidade", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidade deletado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return pacienteService.delete(id);
    }


}
