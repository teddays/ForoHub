package com.forohub.ForoHub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
