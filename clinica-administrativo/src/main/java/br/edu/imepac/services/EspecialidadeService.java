package br.edu.imepac.services;

import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoResponse;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<EspecialidadeDtoResponse> findAll() {
        List<EspecialidadeModel> especialidades = repo.findAll();
        return especialidades.stream()
                .map(especialidade -> modelMapper.map(especialidade, EspecialidadeDtoResponse.class))
                .collect(Collectors.toList());
    }

    public EspecialidadeDtoResponse update(Long id, EspecialidadeDtoResponse dto) {
        Optional<EspecialidadeModel> especialidade = repo.findById(id);
        if (especialidade.isPresent()) {
            EspecialidadeModel especialidadeModel = especialidade.get();
            especialidadeModel.setNome(dto.getNome());
            EspecialidadeModel updatedEspecialidade = repo.save(especialidadeModel);
            return modelMapper.map(updatedEspecialidade, EspecialidadeDtoResponse.class);
        } else {
            return null;
        }
    }

    public EspecialidadeDtoResponse save(EspecialidadeDtoRequest dtoRequest) {
        EspecialidadeModel especialidadeModel = modelMapper.map(dtoRequest, EspecialidadeModel.class);
        EspecialidadeModel savedEspecialidade = repo.save(especialidadeModel);
        return modelMapper.map(savedEspecialidade, EspecialidadeDtoResponse.class);
    }

    public EspecialidadeDtoResponse getById(Long id) {
        Optional<EspecialidadeModel> especialidadeModel = repo.findById(id);
        if (especialidadeModel.isPresent()) {
            return modelMapper.map(especialidadeModel.get(), EspecialidadeDtoResponse.class);
        } else {
            return null;
        }
    }
}
