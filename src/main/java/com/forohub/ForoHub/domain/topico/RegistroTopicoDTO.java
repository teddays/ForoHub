package com.forohub.ForoHub.domain.topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroTopicoDTO(@NotBlank(message = "No puede estar vacío")
                                  String titulo,
                                @NotBlank(message = "No puede estar vacio")
                                  String mensaje,
                                @NotBlank(message = "No puede estar vacio")
                                  String autor,
                                @NotNull(message = "No puede estar vacio")
                                  Curso curso) {
}
