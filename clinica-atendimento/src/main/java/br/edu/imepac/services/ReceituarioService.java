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

import br.edu.imepac.dtos.Prontuario.ProntuarioDtoRequest;
import br.edu.imepac.dtos.Prontuario.ProntuarioDtoResponse;
import br.edu.imepac.dtos.Receituario.ReceituarioDtoRequest;
import br.edu.imepac.dtos.Receituario.ReceituarioDtoResponse;
import br.edu.imepac.models.Prontuario;
import br.edu.imepac.models.Receituario;
import br.edu.imepac.repositories.ReceituarioRepository;

@Service
public class ReceituarioService {
    @Autowired
    private ReceituarioRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ReceituarioService.class);

    public ResponseEntity<String> delete(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok() .body("{\"mensagem\":\"Excluido com sucesso!\"}");
    
    }
    public List<ReceituarioDtoResponse> findAll() {
        logger.info("Receituario Service List All Receituario");
        List<Receituario> receituarios = repository.findAll();
        return receituarios.stream()
            .map(receituario -> modelMapper.map(receituario, ReceituarioDtoResponse.class))
            .collect(Collectors.toList());
    }

    public ReceituarioDtoResponse update(Long Id, ReceituarioDtoRequest receituarioDtoRequest) {
        logger.info("Receituario Service Updating Receituario");
        Optional<Receituario> optionalReceituario = repository.findById(Id);
        if (optionalReceituario.isPresent()) {
            Receituario receituario = optionalReceituario.get();
            logger.info("Receituario Service Updated");
            List<String> newMedicamentos = receituarioDtoRequest.getMedicamentos();
            receituario.setMedicamentos(newMedicamentos);
            Receituario updateReceituario = repository.save(receituario);
            return modelMapper.map(updateReceituario, ReceituarioDtoResponse.class);

        } else{
            return null;
        }
    }
    public ReceituarioDtoResponse save(ReceituarioDtoRequest receituarioDtoRequest){
        Receituario receituario = modelMapper.map(receituarioDtoRequest, Receituario.class);
        Receituario savReceituario = repository.save(receituario);
        return modelMapper.map(savReceituario, ReceituarioDtoResponse.class);

    }
    public ReceituarioDtoResponse getById(Long Id){
    Optional<Receituario> receituario = repository.findById(Id);
    if (receituario.isPresent()) {
        Receituario receituarios = receituario.get();
        ReceituarioDtoResponse receituarioDto = modelMapper.map(receituarios, ReceituarioDtoResponse.class);
        return receituarioDto;
        
    } else{
        return null;
    }
    }
    
}