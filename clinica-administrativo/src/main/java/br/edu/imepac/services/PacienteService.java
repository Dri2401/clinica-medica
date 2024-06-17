package br.edu.imepac.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.imepac.dtos.paciente.PacienteDtoRequest;
import br.edu.imepac.dtos.paciente.PacienteDtoResponse;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.repositories.ConvenioRepository;
import br.edu.imepac.repositories.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    public ResponseEntity<String> delete(Long id){
        logger.info("Paciente Service delete");
        pacienteRepository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public PacienteDtoResponse save(PacienteDtoRequest pacienteDetails){
        logger.info("Paciente Service save Paciente");
        PacienteModel paciente = modelMapper.map(pacienteDetails, PacienteModel.class);
        if (pacienteDetails.getConvenio() != null) {
            ConvenioModel convenio = convenioRepository.findById(pacienteDetails.getConvenio())
                .orElseThrow(() -> new IllegalArgumentException("Convênio não encontrado: " + pacienteDetails.getConvenio()));
            paciente.setConvenio(convenio);
        }
        PacienteModel pacienteSaved = pacienteRepository.save(paciente);
        PacienteDtoResponse dto = modelMapper.map(pacienteSaved, PacienteDtoResponse.class);
        return dto;
    }

    public List<PacienteDtoResponse> findAll(){
        logger.info("Paciente Service Find All Pacientes");
        List<PacienteModel> pacientes = pacienteRepository.findAll();
        List<PacienteDtoResponse> dto = pacientes.stream()
        .map(paciente -> modelMapper.map(paciente, PacienteDtoResponse.class))
        .collect(Collectors.toList());
        return dto;
    }

    public PacienteDtoResponse update(Long id, PacienteDtoRequest pacienteDetails){
        logger.info("Paciente Service Updating Paciente");
        Optional<PacienteModel> optionalPaciente = pacienteRepository.findById(id);

        if(optionalPaciente.isPresent()){
            PacienteModel pacienteModel = optionalPaciente.get();
            Optional.ofNullable(pacienteDetails.getNome()).ifPresent(pacienteModel::setNome);
            Optional.ofNullable(pacienteDetails.getCpf()).ifPresent(pacienteModel::setCpf);
            if (pacienteDetails.getConvenio() != null) {
                ConvenioModel convenio = convenioRepository.findById(pacienteDetails.getConvenio())
                    .orElseThrow(() -> new IllegalArgumentException("Convênio não encontrado: " + pacienteDetails.getConvenio()));
                pacienteModel.setConvenio(convenio);
            } else {
                pacienteModel.setConvenio(null); // Limpar o convênio se convenioId for null
            }
            logger.info("Paciente Service Start Update Paciente");
            PacienteModel updatedPaciente = pacienteRepository.save(pacienteModel);
            logger.info("Updated Paciente");
            PacienteDtoResponse dto = modelMapper.map(updatedPaciente, PacienteDtoResponse.class);
            return dto;
        } else {
            logger.error("Paciente Service Update. Paciente not found");
            return null;
        }
    }
    
    public PacienteDtoResponse findById(Long id){
        logger.info("Paciente Service find By Id");
        Optional<PacienteModel> optionalPaciente = pacienteRepository.findById(id);
        if(optionalPaciente.isPresent()){
            PacienteModel paciente = optionalPaciente.get();
            PacienteDtoResponse dto = modelMapper.map(paciente, PacienteDtoResponse.class);
            return dto;
        } else{
            logger.error("Paciente Service findById. Paciente not found");
            return null;
        }
    }
}
