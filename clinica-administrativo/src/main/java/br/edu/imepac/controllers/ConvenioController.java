package br.edu.imepac.controllers;

import br.edu.imepac.dtos.Convenio.ConvenioDtoRequest;
import br.edu.imepac.dtos.Convenio.ConvenioDtoResponse;
import br.edu.imepac.services.ConvenioService;
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
@RequestMapping("convenio")
@Tag(name = "Convenio-API")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    private static final Logger logger = LoggerFactory.getLogger(ConvenioController.class);

    @Operation(summary = "Realiza o cadastro de convênios", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro do convênio realizada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<ConvenioDtoResponse> saveConvenio(@RequestBody ConvenioDtoRequest convenioDtoRequest) {
        logger.info("Request ConvenioCreateRequest ");
        ConvenioDtoResponse saveConvenio = convenioService.save(convenioDtoRequest);
        return new ResponseEntity<>(saveConvenio, HttpStatus.CREATED);
    }

    
    @Operation(summary = "Retorna todos os convênios cadastrados!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<ConvenioDtoResponse>> getAllConvenio() {
        logger.info("Request ConvenioGetAllRequest ");
        List<ConvenioDtoResponse> convenios = convenioService.findAll();
        return new ResponseEntity<>(convenios, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de um convênio pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConvenioDtoResponse> getConvenioById(@PathVariable("id") Long id) {
        ConvenioDtoResponse convenio = convenioService.getById(id);
        if(convenio != null) {
            logger.info("Request ConvenioFindByIdRequest ");
            return new ResponseEntity<>(convenio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza os dados de um convênio", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Convênio atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConvenioDtoResponse> updateConvenio(@PathVariable("id") Long id, @RequestBody ConvenioDtoResponse convenioDtoResponse) {
        logger.info("Request ConvenioUpdateRequest ");
        ConvenioDtoResponse updateConvenio = convenioService.update(id, convenioDtoResponse);
        if(updateConvenio != null) {
            return new ResponseEntity<>(updateConvenio, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Exclusão de um convênio", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Convênio deletado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteConvenio(@PathVariable("id") Long id) {
        logger.info("Request ConvenioDeleteRequest ");
        return convenioService.delete(id);
    }
}
