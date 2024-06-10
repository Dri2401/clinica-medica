package br.edu.imepac.services;

import br.edu.imepac.dtos.MedicoDtoRequest;
import br.edu.imepac.dtos.MedicoDtoResponse;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void delete(Long id) {
        medicoRepository.deleteById(id);
    }

    public List<MedicoDtoResponse> findAll() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(medico -> modelMapper.map(medico, MedicoDtoResponse.class))
                .collect(Collectors.toList());
    }

    public MedicoDtoResponse update(Long id, MedicoDtoResponse medicoDetails) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            medicoModel.setNome(medicoDetails.getNome());
            medicoModel.setCrm(medicoDetails.getCrm());

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
