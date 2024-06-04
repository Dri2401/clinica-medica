package br.edu.imepac.services;

import br.edu.imepac.dtos.ConvenioDtoRequest;
import br.edu.imepac.dtos.ConvenioDtoResponse;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvenioService {
    @Autowired
    private ConvenioRepository repository;

    public void delete(Long id) { repository.deleteById(id); }

    public List<ConvenioDtoResponse> findAll() {
        List<ConvenioModel> convenios = repository.findAll();
        return convenios.stream().map(convenio -> {
            ConvenioDtoResponse dto = new ConvenioDtoResponse();
            dto.setId(convenio.getId());
            dto.setNome(convenio.getNome());
            return dto;
        }).collect(Collectors.toList());
    }

    public ConvenioDtoResponse update(Long id, ConvenioDtoResponse convenioDtoResponse) {
        Optional<ConvenioModel> convenio = repository.findById(id);
        if(convenio.isPresent()) {
            ConvenioModel convenioModel = convenio.get();
            convenioModel.setNome(convenioDtoResponse.getNome());
            ConvenioModel convenios = repository.save(convenioModel);
            ConvenioDtoResponse dto = new ConvenioDtoResponse();
            dto.setId(id);
            dto.setNome(convenioDtoResponse.getNome());
            return dto;
        } else {
            return null;
        }
    }

    public ConvenioDtoResponse save(ConvenioDtoRequest convenioDtoRequest) {
        ConvenioModel convenioModel = new ConvenioModel();
        convenioModel.setNome(convenioDtoRequest.getNome());
        ConvenioModel convenios = repository.save(convenioModel);
        ConvenioDtoResponse dto = new ConvenioDtoResponse();
        dto.setId(convenios.getId());
        dto.setNome(convenios.getNome());
        return dto;
    }

    public ConvenioDtoResponse getById(Long id) {
        Optional<ConvenioModel> convenio = repository.findById(id);
        if(convenio.isPresent()) {
            ConvenioDtoResponse dto = new ConvenioDtoResponse();
            dto.setId(id);
            dto.setNome(convenio.get().getNome());
            return dto;
        } else {
            return null;
        }
    }
}
