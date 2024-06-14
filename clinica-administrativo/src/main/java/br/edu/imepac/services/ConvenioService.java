package br.edu.imepac.services;

import br.edu.imepac.dtos.Convenio.ConvenioDtoRequest;
import br.edu.imepac.dtos.Convenio.ConvenioDtoResponse;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<ConvenioDtoResponse> findAll() {
        List<ConvenioModel> convenios = repository.findAll();
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDtoResponse.class))
                .collect(Collectors.toList());
    }

    public ConvenioDtoResponse update(Long id, ConvenioDtoResponse convenioDtoResponse) {
        Optional<ConvenioModel> convenio = repository.findById(id);
        if (convenio.isPresent()) {
            ConvenioModel convenioModel = convenio.get();
            convenioModel.setNome(convenioDtoResponse.getNome());
            ConvenioModel updatedConvenio = repository.save(convenioModel);
            return modelMapper.map(updatedConvenio, ConvenioDtoResponse.class);
        } else {
            return null;
        }
    }

    public ConvenioDtoResponse save(ConvenioDtoRequest convenioDtoRequest) {
        ConvenioModel convenioModel = modelMapper.map(convenioDtoRequest, ConvenioModel.class);
        ConvenioModel savedConvenio = repository.save(convenioModel);
        return modelMapper.map(savedConvenio, ConvenioDtoResponse.class);
    }

    public ConvenioDtoResponse getById(Long id) {
        Optional<ConvenioModel> convenio = repository.findById(id);
        if (convenio.isPresent()) {
            return modelMapper.map(convenio.get(), ConvenioDtoResponse.class);
        } else {
            return null;
        }
    }
}
