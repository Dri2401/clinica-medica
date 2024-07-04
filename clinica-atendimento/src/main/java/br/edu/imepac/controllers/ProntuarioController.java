package br.edu.imepac.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.services.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("prontuario")
@Tag(name = "Prontuario-API")
public class ProntuarioController {
    @Autowired
    private ProntuarioService prontuarioService;

    @Operation(summary = "Realiza o cadastro de entidades", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro de entidade realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<ProntuarioDtoResponse> saveProntuario(@RequestBody ProntuarioDtoRequest prontuario){
        ProntuarioDtoResponse dto = prontuarioService.save(prontuario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }

    @Operation(summary = "Retorna todos as entidades cadastradas!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<ProntuarioDtoResponse>> listAllProntuario(){
        List<ProntuarioDtoResponse> dto = prontuarioService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o dado de entidade pelo id", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Entidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProntuarioDtoResponse> getProntuarioById(@PathVariable("id")Long id) {
        ProntuarioDtoResponse dto = prontuarioService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }

      @Operation(summary = "Atualiza os dados de uma entidade", method = "PUT")
      @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidade atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Entidade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
      })
      @PutMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<ProntuarioDtoResponse> updateProntuario(@PathVariable("id")Long id, @RequestBody ProntuarioDtoRequest prontuarioDetails){
        ProntuarioDtoResponse dto = prontuarioService.update(id, prontuarioDetails);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
            
        } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      }

      @Operation(summary = "Exclusão de uma entidade", method = "DELETE")
      @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidade deletado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
      })
      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<String> delete(@PathVariable("id")Long id){
        return prontuarioService.delete(id);
    
      }

    
}
