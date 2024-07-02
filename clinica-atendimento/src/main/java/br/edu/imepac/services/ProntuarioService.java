package br.edu.imepac.services;

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

import br.edu.imepac.dtos.Exames.ExameDtoRequest;
import br.edu.imepac.dtos.Exames.ExamesDtoResponse;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.models.Exames;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.repositories.ExamesRepository;
import br.edu.imepac.repositories.ProntuarioRepository;

@Service
public class ProntuarioService {
    @Autowired
    private ProntuarioRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProntuarioService.class);

    public ResponseEntity<String> delete(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok() .body("{\"mensagem\":\"Excluido com sucesso!\"}");
    
    }
    public List<ProntuarioDtoResponse> findAll() {
        logger.info("Prontuario Service List All Prontuario");
        List<Prontuario> prontuario = repository.findAll();
        return prontuario.stream()
            .map(prontuarios -> modelMapper.map(prontuarios, ProntuarioDtoResponse.class))
            .collect(Collectors.toList());


    }

    public ProntuarioDtoResponse update(Long Id, ProntuarioDtoRequest prontuarioDtoRequest) {
        logger.info("Prontuario Service Updating Prontuario");
        Optional<Prontuario> optionalProntuario = repository.findById(Id);
        if (optionalProntuario.isPresent()) {
            Prontuario prontuario = optionalProntuario.get();
            logger.info("Prontuario Service Updated");
            Prontuario updateProntuario = repository. save(prontuario);
            return modelMapper.map(updateProntuario, ProntuarioDtoResponse.class);

        } else{
            return null;
        }
    }
    public ProntuarioDtoResponse save(ProntuarioDtoRequest prontuarioDtoRequest){
        Prontuario prontuario = modelMapper.map(prontuarioDtoRequest, Prontuario.class);
        Prontuario savProntuario = repository.save(prontuario);
        return modelMapper.map(savProntuario, ProntuarioDtoResponse.class);

    }
    public ProntuarioDtoResponse getById(Long Id){
    Optional<Prontuario> prontuario = repository.findById(Id);
    if (prontuario.isPresent()) {
        Prontuario prontuarios = prontuario.get();
        Map<String, Object> paciente = getPacienteById(prontuarios.getPacienteId());
        String nomePaciente = (String) paciente.get("nome");
        ProntuarioDtoResponse prontuarioDto = modelMapper.map(prontuarios, ProntuarioDtoResponse.class);
        prontuarioDto.setPacienteNome(nomePaciente);
        return prontuarioDto;
        
    } else{
        return null;
    }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getPacienteById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/pacientes/" + id;
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode()==HttpStatus.OK) {
            return response.getBody();
            
        } else{
            return null;
        }
    }
    
}
