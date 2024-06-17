package br.edu.imepac.dtos.paciente;

import br.edu.imepac.models.ConvenioModel;
import lombok.Data;

@Data
public class PacienteDtoResponse {
    private Long id;
    private String nome;
    private ConvenioModel convenio;
}
