package br.edu.imepac.controllers;

import br.edu.imepac.dtos.Medico.MedicoDtoRequest;
import br.edu.imepac.dtos.Medico.MedicoDtoResponse;
import br.edu.imepac.services.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medico")
@Tag(name = "Medico-API")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);


    @Operation(summary = "Realiza o cadastro de médicos", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na request feita pelo usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<MedicoDtoResponse> saveDoctor(@RequestBody MedicoDtoRequest medicoCreateRequest) {
        logger.info("Request MedicoCreateRequest ");
        MedicoDtoResponse savedMedico = medicoService.save(medicoCreateRequest);
        return new ResponseEntity<>(savedMedico, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os médicos", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<MedicoDtoResponse>> listAllDoctors() {
        logger.info("Request MedicoListAllDoctors ");
        List<MedicoDtoResponse> medicos = medicoService.findAll();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @Operation(summary = "Retorna os dados de um médico", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicoDtoResponse> getDoctorById(@PathVariable("id") Long id) {
        MedicoDtoResponse medicoDto = medicoService.findById(id);
        if (medicoDto != null) {
            logger.info("Request MedicoGetDoctorById");
            return new ResponseEntity<>(medicoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Atualiza os dados de um médico", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro intenro")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicoDtoResponse> updateDoctor(@PathVariable("id") Long id, @RequestBody MedicoDtoRequest medicoDetails) {
        MedicoDtoResponse updatedMedico = medicoService.update(id, medicoDetails);
        if (updatedMedico != null) {
            logger.info("Request MedicoUpdate");
            return new ResponseEntity<>(updatedMedico, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Operation(summary = "Exclusão de um médico", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico deletado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro intenro")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Long id) {
        logger.info("Request MedicoDelete");
        return medicoService.delete(id);
    }
}
