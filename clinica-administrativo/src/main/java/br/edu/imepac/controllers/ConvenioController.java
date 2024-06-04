package br.edu.imepac.controllers;

import br.edu.imepac.dtos.ConvenioDtoRequest;
import br.edu.imepac.dtos.ConvenioDtoResponse;
import br.edu.imepac.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("convenio")
public class ConvenioController {
    @Autowired
    private ConvenioService convenioService;

    @PostMapping
    public ResponseEntity<ConvenioDtoResponse> saveConvenio(@RequestBody ConvenioDtoRequest convenioDtoRequest) {
        ConvenioDtoResponse saveConvenio = convenioService.save(convenioDtoRequest);
        return new ResponseEntity<>(saveConvenio, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConvenioDtoResponse>> getAllConvenio() {
        List<ConvenioDtoResponse> convenios = convenioService.findAll();
        return new ResponseEntity<>(convenios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConvenioDtoResponse> getConvenioById(@PathVariable Long id) {
        ConvenioDtoResponse convenio = convenioService.getById(id);
        if(convenio != null) {
            return new ResponseEntity<>(convenio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConvenioDtoResponse> updateConvenio(@PathVariable Long id, @RequestBody ConvenioDtoResponse convenioDtoResponse) {
        ConvenioDtoResponse updateConvenio = convenioService.update(id, convenioDtoResponse);
        if(updateConvenio != null) {
            return new ResponseEntity<>(updateConvenio, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConvenio(@PathVariable Long id) {
        convenioService.delete(id);
    }
}
