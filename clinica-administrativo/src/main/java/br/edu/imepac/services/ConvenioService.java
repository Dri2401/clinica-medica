package br.edu.imepac.services;

import br.edu.imepac.dtos.Convenio.ConvenioDtoRequest;
import br.edu.imepac.dtos.Convenio.ConvenioDtoResponse;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ConvenioService.class);

    public ResponseEntity<String> delete(Long id) {
        logger.info("Convenio Service Deleting convenio");
        repository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public List<ConvenioDtoResponse> findAll() {
        logger.info("Convenio Service List All Convenios");
        List<ConvenioModel> convenios = repository.findAll();
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDtoResponse.class))
                .collect(Collectors.toList());
    }

    public ConvenioDtoResponse update(Long id, ConvenioDtoResponse convenioDtoResponse) {
        logger.info("Convenio Service Updating Convenio");
        Optional<ConvenioModel> convenio = repository.findById(id);
        if (convenio.isPresent()) {
            ConvenioModel convenioModel = convenio.get();
            convenioModel.setNome(convenioDtoResponse.getNome());
            logger.info("Convenio Service Updated");
            ConvenioModel updatedConvenio = repository.save(convenioModel);
            logger.info("Convenio Service Update Complete");
            return modelMapper.map(updatedConvenio, ConvenioDtoResponse.class);
        } else {
            return null;
        }
    }

    public ConvenioDtoResponse save(ConvenioDtoRequest convenioDtoRequest) {
        logger.info("Convenio Service Saving Convenio");
        ConvenioModel convenioModel = modelMapper.map(convenioDtoRequest, ConvenioModel.class);
        logger.info("Convenio Service Save Convenio");
        ConvenioModel savedConvenio = repository.save(convenioModel);
        return modelMapper.map(savedConvenio, ConvenioDtoResponse.class);
    }

    public ConvenioDtoResponse getById(Long id) {
        logger.info("Convenio Service Getting by Id");
        Optional<ConvenioModel> convenio = repository.findById(id);
        if (convenio.isPresent()) {
            return modelMapper.map(convenio.get(), ConvenioDtoResponse.class);
        } else {
            return null;
        }
    }
}
