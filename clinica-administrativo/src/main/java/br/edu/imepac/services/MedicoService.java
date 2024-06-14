package br.edu.imepac.services;

import br.edu.imepac.dtos.Medico.MedicoDtoRequest;
import br.edu.imepac.dtos.Medico.MedicoDtoResponse;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    private MedicoRepository medicoRepository;

    private ModelMapper modelMapper;

    public MedicoService(MedicoRepository medicoRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.medicoRepository = medicoRepository;
    }

    public ResponseEntity<String> delete(Long id) {
        medicoRepository.deleteById(id);
        return ResponseEntity.ok().body("{\"messagem\": \"Excluido com sucesso!\"}");
    }

    public List<MedicoDtoResponse> findAll() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        List<MedicoDtoResponse> dto = medicos.stream()
                .map(medico -> modelMapper.map(medico, MedicoDtoResponse.class))
                .collect(Collectors.toList());
        return dto;
    }

    public MedicoDtoResponse update(Long id, MedicoDtoRequest medicoDetails) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            Optional.ofNullable(medicoDetails.getNome()).ifPresent(medicoModel::setNome);
            Optional.ofNullable(medicoDetails.getCrm()).ifPresent(medicoModel::setCrm);
            Optional.ofNullable(medicoDetails.getSenha()).ifPresent(medicoModel::setSenha);

            MedicoModel updatedMedico = medicoRepository.save(medicoModel);
            MedicoDtoResponse medicoDto = modelMapper.map(updatedMedico, MedicoDtoResponse.class);
            return medicoDto;
        } else {
            return null;
        }
    }

    public MedicoDtoResponse save(MedicoDtoRequest medicoRequest) {
        MedicoModel medicoModel = modelMapper.map(medicoRequest, MedicoModel.class);
        MedicoModel savedMedico = medicoRepository.save(medicoModel);
        MedicoDtoResponse medicoDto = modelMapper.map(savedMedico, MedicoDtoResponse.class);
        return medicoDto;
    }

    public MedicoDtoResponse findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            MedicoDtoResponse medicoDto = modelMapper.map(medicoModel, MedicoDtoResponse.class);
            return medicoDto;
        } else {
            return null;
        }
    }
}
