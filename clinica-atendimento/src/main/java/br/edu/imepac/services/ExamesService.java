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
import br.edu.imepac.models.Exames;
import br.edu.imepac.repositories.ExamesRepository;

@Service
public class ExamesService {
    @Autowired
    private ExamesRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ExamesService.class);

    public ResponseEntity<String> delete(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok() .body("{\"mensagem\":\"Excluido com sucesso!\"}");
    
    }
    public List<ExamesDtoResponse> findAll() {
        logger.info("Exame Service List All Exames");
        List<Exames> exames = repository.findAll();
        List<ExamesDtoResponse> dtos = exames.stream()
            .map(exame -> modelMapper.map(exame, ExamesDtoResponse.class))
            .collect(Collectors.toList());
        return dtos;
    }

    public ExamesDtoResponse update(Long Id, ExameDtoRequest examesDtoRequest) {
        logger.info("Exame Service Updating Exame");
        Optional<Exames> optionalExames = repository.findById(Id);
        if (optionalExames.isPresent()) {
            Exames exames = optionalExames.get();
            Map<String,Object> medico= getMedicoById(examesDtoRequest.getMedicoSolicitante());
            exames.setMedicoSolicitante((String) medico.get("nome"));
            exames.setTipoExame(examesDtoRequest.getTipoExame());
            logger.info("Exames Service Updated");
            Exames updateExames = repository. save(exames);
            return modelMapper.map(updateExames, ExamesDtoResponse.class);

        } else{
            return null;
        }
    }
    public ExamesDtoResponse save(ExameDtoRequest exameDtoRequest){
       Exames exames = modelMapper.map(exameDtoRequest, Exames.class);
       Map<String,Object> medico= getMedicoById(exameDtoRequest.getMedicoSolicitante());
       String nome= (String)medico.get("nome");
       exames.setMedicoSolicitante(nome);
        Exames savExames = repository.save(exames);
        return modelMapper.map(savExames, ExamesDtoResponse.class);

    }
    public ExamesDtoResponse getById(Long Id){
    Optional<Exames> exame = repository.findById(Id);
    if (exame.isPresent()) {
        return modelMapper.map(exame.get(),ExamesDtoResponse.class);
        
    } else{
        return null;
    }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMedicoById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/medico/" + id;
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode()==HttpStatus.OK) {
            return response.getBody();
            
        } else{
            return null;
        }
    }
    
}
