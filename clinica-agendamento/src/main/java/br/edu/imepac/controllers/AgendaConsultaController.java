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

import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoRequest;
import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoResponse;
import br.edu.imepac.services.AgendaConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("agenda")
@Tag(name = "Agenda-API")
public class AgendaConsultaController {
    @Autowired
    private AgendaConsultaService agendaConsultaService;

    private static final Logger logger = LoggerFactory.getLogger(AgendaConsultaController.class);

    @Operation(summary = "Realiza o cadastro de entidades", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro de entidade realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<AgendaConsultaDtoResponse> saveagendaConsulta(@RequestBody AgendaConsultaDtoRequest agendaConsultaDetails){
        logger.info("agendaConsulta Controller create agendaConsulta");
        AgendaConsultaDtoResponse dto = agendaConsultaService.save(agendaConsultaDetails);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos as entidades cadastradas!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<AgendaConsultaDtoResponse>> findAll(){
        List<AgendaConsultaDtoResponse> dto = agendaConsultaService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de entidade pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Entidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendaConsultaDtoResponse> findById(@PathVariable("id") Long id){
        AgendaConsultaDtoResponse dto = agendaConsultaService.findById(id);
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
    public ResponseEntity<AgendaConsultaDtoResponse> update(@PathVariable("id") Long id, @RequestBody AgendaConsultaDtoRequest agendaConsultaDetails){
        AgendaConsultaDtoResponse dto = agendaConsultaService.update(id, agendaConsultaDetails);
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
        return agendaConsultaService.delete(id);
    }




}
