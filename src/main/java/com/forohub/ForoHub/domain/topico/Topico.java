package com.forohub.ForoHub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private String autor;
    @Enumerated(EnumType.STRING)
    private Curso curso;
    private Boolean sinRespuesta = true;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    public Topico(RegistroTopicoDTO registroTopicoDTO) {
        this.titulo = registroTopicoDTO.titulo();
        this.mensaje = registroTopicoDTO.mensaje();
        this.autor = registroTopicoDTO.autor();
        this.curso = registroTopicoDTO.curso();
        this.sinRespuesta = true;
    }

    public void actualizarTopico(ActualizarTopicoDTO actualizarTopicoDTO) {
        if (actualizarTopicoDTO.titulo() != null) {
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if (actualizarTopicoDTO.autor() != null) {
            this.autor = actualizarTopicoDTO.autor();
        }
        if (actualizarTopicoDTO.curso() != null) {
            this.curso = actualizarTopicoDTO.curso();
        }
    }
}
