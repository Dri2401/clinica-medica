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

import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoRequest;
import br.edu.imepac.dtos.AgendaConsulta.AgendaConsultaDtoResponse;
import br.edu.imepac.model.AgendaConsultaModel;
import br.edu.imepac.repositories.AgendaConsultaRepository;

@Service
public class AgendaConsultaService {
    
@Autowired
    private AgendaConsultaRepository agendaConsultaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AgendaConsultaService.class);

    public ResponseEntity<String> delete(Long id){
        logger.info("AgendaConsulta Service delete");
        agendaConsultaRepository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public AgendaConsultaDtoResponse save(AgendaConsultaDtoRequest agendaConsultaDetails){
        logger.info("AgendaConsulta Service save AgendaConsulta");
        AgendaConsultaModel agendaConsulta = modelMapper.map(agendaConsultaDetails, AgendaConsultaModel.class);
        AgendaConsultaModel agendaConsultaSaved = agendaConsultaRepository.save(agendaConsulta);
        Map<String, Object> medico = getMedicoById(agendaConsultaDetails.getMedicoID());
        String medicoNome = (String) medico.get("nome");
        AgendaConsultaDtoResponse dto = modelMapper.map(agendaConsultaSaved, AgendaConsultaDtoResponse.class);
        dto.setMedicoNome(medicoNome);
        return dto;
    }

    public List<AgendaConsultaDtoResponse> findAll(){
        logger.info("AgendaConsulta Service Find All AgendaConsulta");
        List<AgendaConsultaModel> agendaConsulta = agendaConsultaRepository.findAll();
        List<AgendaConsultaDtoResponse> dtos = new ArrayList<>();
        for(AgendaConsultaModel agenda : agendaConsulta){
            AgendaConsultaDtoResponse dto = modelMapper.map(agenda, AgendaConsultaDtoResponse.class);
            if(agenda.getMedicoID() != null){
                Map<String, Object> medico = getMedicoById(agenda.getMedicoID());
                String nomeMedico = (String) medico.get("nome");
                dto.setMedicoNome(nomeMedico);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public AgendaConsultaDtoResponse update(Long id, AgendaConsultaDtoRequest agendaConsultaDetails){
        logger.info("AgendaConsulta Service Updating AgendaConsulta");
        Optional<AgendaConsultaModel> optionalAgendaConsulta = agendaConsultaRepository.findById(id);

        if(optionalAgendaConsulta.isPresent()){
            AgendaConsultaModel agendaConsultaModel = optionalAgendaConsulta.get();
            Optional.ofNullable(agendaConsultaDetails.getDataConsulta()).ifPresent(agendaConsultaModel::setDataConsulta);
            Optional.ofNullable(agendaConsultaDetails.getHorario()).ifPresent(agendaConsultaModel::setHorario);
            Optional.ofNullable(agendaConsultaDetails.getMedicoID()).ifPresent(agendaConsultaModel::setMedicoID);
            Optional.ofNullable(agendaConsultaDetails.getNomePaciente()).ifPresent(agendaConsultaModel::setNomePaciente);
            Map<String, Object> medico = getMedicoById(id);
            String medicoNome = (String) medico.get("nome");
            logger.info("AgendaConsulta Service Start Update AgendaConsulta");
            AgendaConsultaModel updatedAgendaConsulta = agendaConsultaRepository.save(agendaConsultaModel);
            logger.info("Updated AgendaConsulta");
            AgendaConsultaDtoResponse dto = modelMapper.map(updatedAgendaConsulta, AgendaConsultaDtoResponse.class);
            dto.setMedicoNome(medicoNome);
            return dto;
        } else {
            logger.error("AgendaConsulta Service Update. AgendaConsulta not found");
            return null;
        }
    }
    
    public AgendaConsultaDtoResponse findById(Long id){
        logger.info("AgendaConsulta Service find By Id");
        Optional<AgendaConsultaModel> optionalAgendaConsulta = agendaConsultaRepository.findById(id);
        if(optionalAgendaConsulta.isPresent()){
            AgendaConsultaModel agendaConsulta = optionalAgendaConsulta.get();
            Map<String, Object> medico = getMedicoById(agendaConsulta.getMedicoID());
            String nomeMedico = (String) medico.get("nome");
            AgendaConsultaDtoResponse dto = modelMapper.map(agendaConsulta, AgendaConsultaDtoResponse.class);
            dto.setMedicoNome(nomeMedico);
            return dto;
        } else{
            logger.error("Paciente Service findById. Paciente not found");
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
