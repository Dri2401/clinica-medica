package br.edu.imepac.controllers;


import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoResponse;
import br.edu.imepac.services.EspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("especialidade")
@Tag(name = "Especialidade-API")
public class EspecialidadeController {
    @Autowired
    private EspecialidadeService especialidadeService;

    private static final Logger logger = LoggerFactory.getLogger(EspecialidadeController.class);

    @Operation(summary = "Realiza o cadastro de Especialidades", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro da especialidade realizada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<EspecialidadeDtoResponse> saveEspecialidade(@RequestBody EspecialidadeDtoRequest especialidadeDtoRequest) {
        logger.info("Request EspecialidadeCrateRequest ");
        EspecialidadeDtoResponse dto = especialidadeService.save(especialidadeDtoRequest);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos as especialidades cadastradas!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<EspecialidadeDtoResponse>> listAllEspecialidades() {
        logger.info("Request listAllEspecialidades ");
        List<EspecialidadeDtoResponse> dto = especialidadeService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de uma especialidade pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especialidade não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EspecialidadeDtoResponse> getEspecialidadeById(@PathVariable("id") Long id) {
        EspecialidadeDtoResponse dto = especialidadeService.getById(id);
        if (dto != null) {
            logger.info("Request EspecialidadeCrateRequest ");
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Atualiza uma especialidade", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Especialidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EspecialidadeDtoResponse> updateEspecialidade(@PathVariable("id") Long id, @RequestBody EspecialidadeDtoResponse especialidadeDtoResponse) {
        EspecialidadeDtoResponse dto = especialidadeService.update(id, especialidadeDtoResponse);
        if (dto != null) {
            logger.info("Request EspecialidadeCrateRequest ");
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Exclusão de uma especialidade", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidade deletada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEspecialidade(@PathVariable("id") Long id) {
        logger.info("Request deleteEspecialidade ");
        return especialidadeService.delete(id);
    }
}
