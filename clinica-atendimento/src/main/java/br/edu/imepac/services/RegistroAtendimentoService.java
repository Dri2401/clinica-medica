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
import org.springframework.web.client.RestTemplate;

import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoRequest;
import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoResponse;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.models.RegistroAtendimento;
import br.edu.imepac.repositories.ProntuarioRepository;
import br.edu.imepac.repositories.RegistroAtendimentoRepository;

public class RegistroAtendimentoService {
    
    @Autowired
    private RegistroAtendimentoRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(RegistroAtendimentoService.class);

    public ResponseEntity<String> delete(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok() .body("{\"mensagem\":\"Excluido com sucesso!\"}");
    
    }
    public List<RegistroAtendimentoDtoResponse> findAll() {
        logger.info("RegistroAtendimento Service List All RegistroAtendimento");
        List<RegistroAtendimento> registroAtendimento = repository.findAll();
        return registroAtendimento.stream()
            .map(registros -> modelMapper.map(registroAtendimento, RegistroAtendimentoDtoResponse.class))
            .collect(Collectors.toList());


    }

    public RegistroAtendimentoDtoResponse update(Long Id, RegistroAtendimentoDtoRequest registroAtendimentoDtoRequest) {
        logger.info("RegistroAtendimento Service Updating RegistroAtendimento");
        Optional<RegistroAtendimento> optionalRegistroAtendimento = repository.findById(Id);
        if (optionalRegistroAtendimento.isPresent()) {
            RegistroAtendimento registroAtendimento = optionalRegistroAtendimento.get();
            logger.info("RegistroAtendimento Service Updated");
            RegistroAtendimento updateRegistroAtendimento = repository. save(registroAtendimento);
            return modelMapper.map(updateRegistroAtendimento, RegistroAtendimentoDtoResponse.class);

        } else{
            return null;
        }
    }
    public RegistroAtendimentoDtoResponse save(RegistroAtendimentoDtoRequest registroAtendimentoDtoRequest){
        RegistroAtendimento registroAtendimento = modelMapper.map(registroAtendimentoDtoRequest, RegistroAtendimento.class);
        RegistroAtendimento savRegistroAtendimento = repository.save(registroAtendimento);
        return modelMapper.map(savRegistroAtendimento, RegistroAtendimentoDtoResponse.class);

    }
    public RegistroAtendimentoDtoResponse getById(Long Id){
    Optional<RegistroAtendimento> registroAtendimento = repository.findById(Id);
    if (registroAtendimento.isPresent()) {
        RegistroAtendimento registrosAtendimento = registroAtendimento.get();
        Map<String, Object> paciente = getPacienteById(registrosAtendimento.getPacienteId());
        String nomePaciente = (String) paciente.get("nome");
        RegistroAtendimentoDtoResponse registroAtendimentoDto = modelMapper.map(registroAtendimento, RegistroAtendimentoDtoResponse.class);
        registroAtendimentoDto.setPacienteNome(nomePaciente);
        return registroAtendimentoDto;
        
    } else{
        return null;
    }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getPacienteById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http:localhost:8081/paciente/" + id;
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode()==HttpStatus.OK) {
            return response.getBody();
            
        } else{
            return null;
        }
    }
    
}
