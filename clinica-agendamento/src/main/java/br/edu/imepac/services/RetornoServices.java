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

import br.edu.imepac.dtos.Retorno.RetornoDtoRequest;
import br.edu.imepac.dtos.Retorno.RetornoDtoResponse;
import br.edu.imepac.dtos.paciente.PacienteDtoRequest;
import br.edu.imepac.dtos.paciente.PacienteDtoResponse;
import br.edu.imepac.model.PacienteModel;
import br.edu.imepac.model.RetornoModel;
import br.edu.imepac.repositories.PacienteRepository;
import br.edu.imepac.repositories.RetornoRepository;

@Service
public class RetornoServices {
   
    @Autowired
    private RetornoRepository retornoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(RetornoServices.class);

    public ResponseEntity<String> delete(Long id){
        logger.info("Retorno Service delete");
        retornoRepository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public RetornoDtoResponse save(RetornoDtoRequest retornoDetails){
        logger.info("Retorno Service save Retorno");
        RetornoModel retorno = modelMapper.map(retornoDetails, RetornoModel.class);
        RetornoModel retornoSaved = retornoRepository.save(retorno);
        Map<String, Object> medico = getMedicoById(retornoDetails.getMedicoId());
        String medicoNome = (String) medico.get("nome");
        RetornoDtoResponse dto = modelMapper.map(retornoSaved, RetornoDtoResponse.class);
        dto.setMedicoNome(medicoNome);
        return dto;
    }

    public List<RetornoDtoResponse> findAll(){
        logger.info("Retorno Service Find All Retorno");
        List<RetornoModel> retorno = retornoRepository.findAll();
        List<RetornoDtoResponse> dto = retorno.stream()
        .map(retono -> modelMapper.map(retono, RetornoDtoResponse.class))
        .collect(Collectors.toList());
        return dto;
    }

    public RetornoDtoResponse update(Long id, RetornoDtoRequest retornoDetails){
        logger.info("Retorno Service Updating Retorno");
        Optional<RetornoModel> optionalRetorno = retornoRepository.findById(id);

        if(optionalRetorno.isPresent()){
            RetornoModel retornoModel = optionalRetorno.get();
            Optional.ofNullable(retornoDetails.getDataRetorno()).ifPresent(retornoModel::setDataRetorno);
            Optional.ofNullable(retornoDetails.getHorario()).ifPresent(retornoModel::setHorario);
            Optional.ofNullable(retornoDetails.getMedicoId()).ifPresent(retornoModel::setMedicoId);
            Optional.ofNullable(retornoDetails.getNomePaciente()).ifPresent(retornoModel::setNomePaciente);
            Map<String, Object> medico = getMedicoById(id);
            String medicoNome = (String) medico.get("nome");
            logger.info("Retorno Service Start Update Retorno");
            RetornoModel updatedretorno = retornoRepository.save(retornoModel);
            logger.info("Updated retorno");
            RetornoDtoResponse dto = modelMapper.map(updatedretorno, RetornoDtoResponse.class);
            dto.setMedicoNome(medicoNome);
            return dto;
        } else {
            logger.error("Retorno Service Update. Retorno not found");
            return null;
        }
    }
    
    public RetornoDtoResponse findById(Long id){
        logger.info("retorno Service find By Id");
        Optional<RetornoModel> optionalretorno = retornoRepository.findById(id);
        if(optionalretorno.isPresent()){
            RetornoModel retorno = optionalretorno.get();
            RetornoDtoResponse dto = modelMapper.map(retorno, RetornoDtoResponse.class);
            return dto;
        } else{
            logger.error("Paciente Service findById. Paciente not found");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMedicoById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http:localhost:8080/medico/" + id;
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode()==HttpStatus.OK) {
            return response.getBody();
            
        } else{
            return null;
        }
    }
}
