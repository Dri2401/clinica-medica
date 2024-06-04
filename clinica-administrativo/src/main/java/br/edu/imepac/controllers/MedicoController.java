package br.edu.imepac.controllers;

import br.edu.imepac.dtos.MedicoDtoResponse;
import br.edu.imepac.dtos.MedicoDtoRequest;
import br.edu.imepac.dtos.MedicoDtoResponse;
import br.edu.imepac.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medico")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;


    @PostMapping
    public ResponseEntity<MedicoDtoResponse> saveDoctor(@RequestBody MedicoDtoRequest medicoCreateRequest) {
        MedicoDtoResponse savedMedico = medicoService.save(medicoCreateRequest);
        return new ResponseEntity<>(savedMedico, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicoDtoResponse>> listAllDoctors() {
        List<MedicoDtoResponse> medicos = medicoService.findAll();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicoDtoResponse> getDoctorById(@PathVariable Long id) {
        MedicoDtoResponse medicoDto = medicoService.findById(id);
        if (medicoDto != null) {
            return new ResponseEntity<>(medicoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicoDtoResponse> updateDoctor(@PathVariable Long id, @RequestBody MedicoDtoResponse medicoDetails) {
        MedicoDtoResponse updatedMedico = medicoService.update(id, medicoDetails);
        if (updatedMedico != null) {
            return new ResponseEntity<>(updatedMedico, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDoctor(@PathVariable Long id) {
        medicoService.delete(id);
    }
}
