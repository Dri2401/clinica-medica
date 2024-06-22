package br.edu.imepac.services;

import br.edu.imepac.dtos.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.EspecialidadeDtoResponse;
import br.edu.imepac.dtos.MedicoDtoResponse;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.models.MedicoModel;
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

    public void delete(Long id) {repo.deleteById(id);}

    public List<EspecialidadeDtoResponse> findAll() {
        List<EspecialidadeModel> especialidades = repo.findAll();
        return especialidades.stream().map(especialidade -> {
            EspecialidadeDtoResponse dto = new EspecialidadeDtoResponse();
            dto.setId(especialidade.getId());
            dto.setNome(especialidade.getNome());
            return dto;
        }).collect(Collectors.toList());
    }

    public EspecialidadeDtoResponse update(Long id, EspecialidadeDtoResponse dto) {
        Optional<EspecialidadeModel> especialidade = repo.findById(id);
        if(especialidade.isPresent()) {
            EspecialidadeModel especialidadeModel = especialidade.get();
            especialidadeModel.setNome(dto.getNome());
            especialidadeModel = repo.save(especialidadeModel);
            EspecialidadeDtoResponse dto2 = new EspecialidadeDtoResponse();
            dto2.setId(especialidadeModel.getId());
            dto2.setNome(especialidadeModel.getNome());
            return dto2;
        } else
            return null;
    }

    public EspecialidadeDtoResponse save(EspecialidadeDtoRequest dtoRequest) {
        EspecialidadeModel especialidadeModel = modelMapper.map(dtoRequest, EspecialidadeModel.class);
        EspecialidadeModel savedEspecialidade = repo.save(especialidadeModel);
        EspecialidadeDtoResponse especialidadeDto = modelMapper.map(savedEspecialidade, EspecialidadeDtoResponse.class);
        return especialidadeDto;
    }

    public EspecialidadeDtoResponse getById(Long id) {
        Optional<EspecialidadeModel> especialidadeModel = repo.findById(id);
        if(especialidadeModel.isPresent()) {
            EspecialidadeDtoResponse dto = new EspecialidadeDtoResponse();
            dto.setId(especialidadeModel.get().getId());
            dto.setNome(especialidadeModel.get().getNome());
            return dto;
        } else
            return null;
    }
}
