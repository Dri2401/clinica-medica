package br.edu.imepac.controllers;


import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoResponse;
import br.edu.imepac.services.EspecialidadeService;
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
public class EspecialidadeController {
    @Autowired
    private EspecialidadeService especialidadeService;

    private static final Logger logger = LoggerFactory.getLogger(EspecialidadeController.class);


    @PostMapping
    public ResponseEntity<EspecialidadeDtoResponse> saveEspecialidade(@RequestBody EspecialidadeDtoRequest especialidadeDtoRequest) {
        logger.info("Request EspecialidadeCrateRequest ");
        EspecialidadeDtoResponse dto = especialidadeService.save(especialidadeDtoRequest);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeDtoResponse>> listAllEspecialidades() {
        logger.info("Request listAllEspecialidades ");
        List<EspecialidadeDtoResponse> dto = especialidadeService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEspecialidade(@PathVariable("id") Long id) {
        logger.info("Request deleteEspecialidade ");
        return especialidadeService.delete(id);
    }
}
