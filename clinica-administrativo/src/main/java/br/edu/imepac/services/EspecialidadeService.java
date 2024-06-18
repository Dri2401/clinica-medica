package br.edu.imepac.services;

import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoRequest;
import br.edu.imepac.dtos.Especialidade.EspecialidadeDtoResponse;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
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
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(EspecialidadeService.class);

    public ResponseEntity<String> delete(Long id) {
        logger.info("Especialidade Service Deleting Especialidade");
        repo.deleteById(id);
        return ResponseEntity.ok().body("{\"mensagem\": \"Excluido com sucesso!\"}");
    }

    public List<EspecialidadeDtoResponse> findAll() {
        logger.info("Especialidade Service Find All Especialidades");
        List<EspecialidadeModel> especialidades = repo.findAll();
        return especialidades.stream()
                .map(especialidade -> modelMapper.map(especialidade, EspecialidadeDtoResponse.class))
                .collect(Collectors.toList());
    }

    public EspecialidadeDtoResponse update(Long id, EspecialidadeDtoRequest dto) {
        logger.info("Especialidade Service Updating Especialidade");
        Optional<EspecialidadeModel> especialidade = repo.findById(id);
        if (especialidade.isPresent()) {
            EspecialidadeModel especialidadeModel = especialidade.get();
            especialidadeModel.setNome(dto.getNome());
            logger.info("Especialidade Service update Especialidade");
            EspecialidadeModel updatedEspecialidade = repo.save(especialidadeModel);
            logger.info("Especialidade Service updated");
            return modelMapper.map(updatedEspecialidade, EspecialidadeDtoResponse.class);
        } else {
            logger.error("Especialidade Service not found Especialidade");
            return null;
        }
    }

    public EspecialidadeDtoResponse save(EspecialidadeDtoRequest dtoRequest) {
        EspecialidadeModel especialidadeModel = modelMapper.map(dtoRequest, EspecialidadeModel.class);
        logger.info("Especialidade Service Save Especialidade");
        EspecialidadeModel savedEspecialidade = repo.save(especialidadeModel);
        return modelMapper.map(savedEspecialidade, EspecialidadeDtoResponse.class);
    }

    public EspecialidadeDtoResponse getById(Long id) {
        logger.info("Especialidade Service get by Id Especialidade ");
        Optional<EspecialidadeModel> especialidadeModel = repo.findById(id);
        if (especialidadeModel.isPresent()) {
            return modelMapper.map(especialidadeModel.get(), EspecialidadeDtoResponse.class);
        } else {
            logger.error("Especialidade Service not found Especialidade");
            return null;
        }
    }
}
