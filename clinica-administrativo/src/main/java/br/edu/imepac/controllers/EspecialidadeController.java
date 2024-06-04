package br.edu.imepac.controllers;


import br.edu.imepac.dtos.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.EspecialidadeDtoResponse;
import br.edu.imepac.services.EspecialidadeService;
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

    @PostMapping
    public ResponseEntity<EspecialidadeDtoResponse> saveEspecialidade(@RequestBody EspecialidadeDtoRequest especialidadeDtoRequest) {
        EspecialidadeDtoResponse dto = especialidadeService.save(especialidadeDtoRequest);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeDtoResponse>> listAllEspecialidades() {
        List<EspecialidadeDtoResponse> dto = especialidadeService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EspecialidadeDtoResponse> getEspecialidadeById(@PathVariable Long id) {
        EspecialidadeDtoResponse dto = especialidadeService.getById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EspecialidadeDtoResponse> updateEspecialidade(@PathVariable Long id, @RequestBody EspecialidadeDtoResponse especialidadeDtoResponse) {
        EspecialidadeDtoResponse dto = especialidadeService.update(id, especialidadeDtoResponse);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEspecialidade(@PathVariable Long id) {
        especialidadeService.delete(id);
    }
}
