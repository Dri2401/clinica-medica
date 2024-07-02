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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoRequest;
import br.edu.imepac.dtos.RegistroAtendimento.RegistroAtendimentoDtoResponse;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.models.RegistroAtendimento;
import br.edu.imepac.repositories.ProntuarioRepository;
import br.edu.imepac.repositories.RegistroAtendimentoRepository;

@Service
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
        logger.info("RegistroAtendimento service already find registro");
        List<RegistroAtendimentoDtoResponse> dtos = new ArrayList<>();
        for(RegistroAtendimento registro : registroAtendimento){
            logger.info("Using modelMapper to format from RegistroAtendimento to DTO");
            RegistroAtendimentoDtoResponse dto = modelMapper.map(registro, RegistroAtendimentoDtoResponse.class);
            if(registro.getPacienteId() != null){
                logger.info("Trying to get paciente Data in API");
                Map<String, Object> paciente = getPacienteById(registro.getPacienteId());
                logger.info("Sucessful request to API");
                logger.info("Trying cast from Object to String");
                String nome = (String) paciente.get("nome");
                logger.info("Cast make sucessfully");
                dto.setPacienteNome(nome);
            }
            dtos.add(dto);
        }
        logger.info("RegistroAtendimento Service ListAll RegistroAtendimento");
        return dtos;

    }

    public RegistroAtendimentoDtoResponse update(Long Id, RegistroAtendimentoDtoRequest registroAtendimentoDtoRequest) {
        logger.info("RegistroAtendimento Service Updating RegistroAtendimento");
        Optional<RegistroAtendimento> optionalRegistroAtendimento = repository.findById(Id);
        if (optionalRegistroAtendimento.isPresent()) {
            RegistroAtendimento registroAtendimento = optionalRegistroAtendimento.get();
            logger.info("RegistroAtendimento Service Updated");
            registroAtendimento.setDescricao(registroAtendimentoDtoRequest.getDescricao());
            registroAtendimento.setPacienteId(registroAtendimentoDtoRequest.getPacienteId());
            RegistroAtendimento updateRegistroAtendimento = repository.save(registroAtendimento);
            Map<String, Object> paciente = getPacienteById(registroAtendimentoDtoRequest.getPacienteId());
            String nome = (String) paciente.get("nome");
            RegistroAtendimentoDtoResponse dto = modelMapper.map(updateRegistroAtendimento, RegistroAtendimentoDtoResponse.class);
            dto.setPacienteNome(nome);
            return dto;

        } else{
            return null;
        }
    }
    public RegistroAtendimentoDtoResponse save(RegistroAtendimentoDtoRequest registroAtendimentoDtoRequest){
        RegistroAtendimento registroAtendimento = modelMapper.map(registroAtendimentoDtoRequest, RegistroAtendimento.class);
        String nome = null;
        logger.info("Request to API");
        try {
            Map<String, Object> paciente = getPacienteById(registroAtendimento.getPacienteId());
            nome = (String) paciente.get("nome");
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("Error request in API to get Paciente");
            throw e;
        }
        RegistroAtendimento savRegistroAtendimento = repository.save(registroAtendimento);
        RegistroAtendimentoDtoResponse dto = modelMapper.map(savRegistroAtendimento, RegistroAtendimentoDtoResponse.class);
        dto.setPacienteNome(nome);
        return dto;

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
        String url = "http://localhost:8081/pacientes/" + id;
        Map<String, Object> paciente = null;
        try {
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            paciente = response.getBody();
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            paciente = null; // Paciente não encontrado
        }
    } catch (HttpClientErrorException e) {
        paciente = null;
        // Log de erro opcional para debugging
        logger.error("Client error when fetching patient ID: " + id, e);
    } catch (HttpServerErrorException e) {
        // Lidar com exceções específicas do servidor (por exemplo, 5xx)
        paciente = null;
        // Log de erro opcional para debugging
        logger.error("Server error when fetching patient ID: " + id, e);
    } catch (Exception e) {
        // Lidar com outras exceções não esperadas
        paciente = null;
        // Log de erro opcional para debugging
        logger.error("Unexpected error when fetching patient ID: " + id, e);
    }
    
    return paciente;
    }
    
}

