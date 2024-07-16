package com.forohub.ForoHub.domain.topico;

public record RespuestaTopicoDTO(Long id,
                                 String titulo,
                                 String mensaje,
                                 String autor,
                                 Curso curso) {
}
