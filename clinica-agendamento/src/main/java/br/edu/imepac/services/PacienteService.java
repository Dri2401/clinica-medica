package br.edu.imepac.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.imepac.dtos.paciente.PacienteDtoRequest;
import br.edu.imepac.dtos.paciente.PacienteDtoResponse;
import br.edu.imepac.model.PacienteModel;
import br.edu.imepac.repositories.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

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
        PacienteModel pacienteSaved = pacienteRepository.save(paciente);
        Map<String, Object> convenio = getConvenioById(pacienteDetails.getConvenioId());
        String convenioNome = (String) convenio.get("nome");
        PacienteDtoResponse dto = modelMapper.map(pacienteSaved, PacienteDtoResponse.class);
        dto.setConvenioNome(convenioNome);
        return dto;
    }

    public List<PacienteDtoResponse> findAll(){
        logger.info("Paciente Service Find All Pacientes");
        List<PacienteModel> pacientes = pacienteRepository.findAll();
        List<PacienteDtoResponse> dtos = new ArrayList<>();

        for (PacienteModel paciente : pacientes) {
            PacienteDtoResponse dto = modelMapper.map(paciente, PacienteDtoResponse.class);
            if (paciente.getConvenioId() != null) {
                Map<String, Object> convenio = getConvenioById(paciente.getConvenioId());
                if (convenio != null) {
                    String convenioNome = (String) convenio.get("nome");
                    dto.setConvenioNome(convenioNome);
                }
            } else {
                dto.setConvenioNome(null); // ou algum valor padrão, se preferir
            }
            dtos.add(dto);
        }
        
        return dtos;
    }


    public PacienteDtoResponse update(Long id, PacienteDtoRequest pacienteDetails){
        logger.info("Paciente Service Updating Paciente");
        Optional<PacienteModel> optionalPaciente = pacienteRepository.findById(id);

        if(optionalPaciente.isPresent()){
            PacienteModel pacienteModel = optionalPaciente.get();
            Optional.ofNullable(pacienteDetails.getNome()).ifPresent(pacienteModel::setNome);
            Optional.ofNullable(pacienteDetails.getCpf()).ifPresent(pacienteModel::setCpf);
            Map<String, Object> convenio = getConvenioById(id);
            String convenioNome = (String) convenio.get("nome");
            logger.info("Paciente Service Start Update Paciente");
            PacienteModel updatedPaciente = pacienteRepository.save(pacienteModel);
            logger.info("Updated Paciente");
            PacienteDtoResponse dto = modelMapper.map(updatedPaciente, PacienteDtoResponse.class);
            dto.setConvenioNome(convenioNome);
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
            Map<String, Object> convenio = getConvenioById(paciente.getConvenioId());
            String convenioNome = (String) convenio.get("nome");
            dto.setConvenioNome(convenioNome);
            return dto;
        } else{
            logger.error("Paciente Service findById. Paciente not found");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getConvenioById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/convenio/" + id;
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode()==HttpStatus.OK) {
            return response.getBody();
            
        } else{
            return null;
        }
    }
}
