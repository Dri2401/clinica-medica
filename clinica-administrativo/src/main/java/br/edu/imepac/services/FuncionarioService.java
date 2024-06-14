package br.edu.imepac.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.imepac.dtos.Funcionarios.FuncionarioDtoRequest;
import br.edu.imepac.dtos.Funcionarios.FuncionarioDtoResponse;
import br.edu.imepac.models.FuncionarioModel;
import br.edu.imepac.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(FuncionarioService.class);

    public ResponseEntity<String> delete(Long id){
        logger.info("Delete Funcionario Service");
        funcionarioRepository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public List<FuncionarioDtoResponse> findAll(){
        logger.info("Find all Funcionario Service");
        List<FuncionarioModel> funcionario = funcionarioRepository.findAll();
        @SuppressWarnings("unchecked")
        List<FuncionarioDtoResponse> dto = (List<FuncionarioDtoResponse>) modelMapper.map(funcionario, FuncionarioDtoResponse.class);
        return dto;
    }

    public FuncionarioDtoResponse update(Long id, FuncionarioDtoRequest funcionarioDetails){
        logger.info("Updating Funcionario Service");
        Optional<FuncionarioModel> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()){
            FuncionarioModel funcionario = optionalFuncionario.get();
            Optional.ofNullable(funcionarioDetails.getNome()).ifPresent(funcionario::setNome);
            Optional.ofNullable(funcionarioDetails.getCargo()).ifPresent(funcionario::setCargo);

            FuncionarioModel updated = funcionarioRepository.save(funcionario);
            FuncionarioDtoResponse dto = modelMapper.map(updated, FuncionarioDtoResponse.class);
            return dto;
        } else {
            return null;
        }
    }

    public FuncionarioDtoResponse save(FuncionarioDtoRequest funcionarioDetails) {
        logger.info("Creating funcionario Service");
        FuncionarioModel funcionario = modelMapper.map(funcionarioDetails, FuncionarioModel.class);
        FuncionarioModel savedFuncionario = funcionarioRepository.save(funcionario);
        FuncionarioDtoResponse dto = modelMapper.map(savedFuncionario, FuncionarioDtoResponse.class);
        return dto;
    }

    public FuncionarioDtoResponse findById(Long id){
        logger.info("Find Funcionario by ID Service");
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);
        FuncionarioDtoResponse dto = modelMapper.map(funcionario, FuncionarioDtoResponse.class);
        return dto;
    }
}
