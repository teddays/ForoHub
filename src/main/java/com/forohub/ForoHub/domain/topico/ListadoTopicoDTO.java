package com.forohub.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record ListadoTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso,
        String status,
        LocalDateTime fechaCreacion
) {

    public ListadoTopicoDTO(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso(), topico.getSinRespuesta() ? "sin respuesta" : "con respuesta", topico.getFechaCreacion());
    }

}
