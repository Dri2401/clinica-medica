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

import br.edu.imepac.dtos.Receituario.ReceituarioDtoRequest;
import br.edu.imepac.dtos.Receituario.ReceituarioDtoResponse;
import br.edu.imepac.services.ReceituarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequestMapping("receituario")
public class ReceituarioController {
    @Autowired
    private ReceituarioService receituarioService;

    @Operation(summary = "Realiza o cadastro de entidades", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro de entidade realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<ReceituarioDtoResponse> saveReceituario(@RequestBody ReceituarioDtoRequest receituario){
        ReceituarioDtoResponse dto = receituarioService.save(receituario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
   }

    @Operation(summary = "Retorna todos as entidades cadastradas!", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<ReceituarioDtoResponse>> listAllReceituario(){
        List<ReceituarioDtoResponse> dto = receituarioService.findAll();
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
    public ResponseEntity<ReceituarioDtoResponse> getReceituarioById(@PathVariable("id")Long id) {
        ReceituarioDtoResponse dto = receituarioService.getById(id);
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
      public ResponseEntity<ReceituarioDtoResponse> updateReceituario(@PathVariable("id")Long id, @RequestBody ReceituarioDtoRequest receituarioDetails){
        ReceituarioDtoResponse dto = receituarioService.update(id, receituarioDetails);
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
        return receituarioService.delete(id);
    
      }

    
}
