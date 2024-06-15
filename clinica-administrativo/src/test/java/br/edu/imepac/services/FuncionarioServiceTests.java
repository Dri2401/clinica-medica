package br.edu.imepac.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.edu.imepac.dtos.Funcionarios.FuncionarioDtoResponse;
import br.edu.imepac.models.FuncionarioModel;
import br.edu.imepac.repositories.FuncionarioRepository;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTests {
    
    @InjectMocks
    FuncionarioService service;

    @Mock
    ModelMapper mapper;

    @Mock
    FuncionarioRepository funcionarioRepository;

    FuncionarioDtoResponse funcionarioDto;
    FuncionarioModel funcionarioModel;

    @BeforeEach
    public void setUp(){
        Long id = Long.parseLong("3");
        funcionarioDto = new FuncionarioDtoResponse(id, "Amanda", "Serviços Gerais");
        funcionarioModel = new FuncionarioModel(id, "Amanda", "Serviços Gerais"); // Certifique-se de que os campos correspondem ao seu modelo
    }

    @Test
    void deveBuscarUmaPessoa(){
        // Configurar mock para o repositório
        when(funcionarioRepository.findById(funcionarioDto.getId())).thenReturn(Optional.of(funcionarioModel));
        // Configurar mock para o mapper
        when(mapper.map(Optional.of(funcionarioModel), FuncionarioDtoResponse.class)).thenReturn(funcionarioDto);

        // Chamar o método do serviço
        FuncionarioDtoResponse resultado = service.findById(funcionarioDto.getId());

        // Verificar o resultado
        assertNotNull(resultado);
        assertEquals(funcionarioDto, resultado);

        // Verificar interações
        verify(funcionarioRepository).findById(funcionarioDto.getId());
        verify(mapper).map(Optional.of(funcionarioModel), FuncionarioDtoResponse.class);
        verifyNoMoreInteractions(funcionarioRepository);
        verifyNoMoreInteractions(mapper);
    }
}
