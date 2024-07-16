package com.forohub.ForoHub.controller;


import com.forohub.ForoHub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Gestión de tópicos del foro")
public class TopicoController {
    @Autowired
    private ITopicoRepository ITopicoRepository;

    @PostMapping
    @Operation(summary = "Crear un nuevo tópico", description = "Crear un nuevo tópico en el foro.")
    public ResponseEntity<RespuestaTopicoDTO> registrarTopico(@RequestBody @Valid RegistroTopicoDTO registroTopicoDTO,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = ITopicoRepository.save(new Topico(registroTopicoDTO));
        RespuestaTopicoDTO respuestaTopicoDTO = new RespuestaTopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(respuestaTopicoDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos los tópicos", description = "Obtener una lista de todos los tópicos del foro.")
    public ResponseEntity<Page<ListadoTopicoDTO>> listadoTopico(@PageableDefault(size = 10) Pageable paginacion) {
        Pageable sortedByFechaCreacionAsc = PageRequest.of(paginacion.getPageNumber(), paginacion.getPageSize(), Sort.by("fechaCreacion").ascending());
        return ResponseEntity.ok(ITopicoRepository.findBySinRespuestaTrue(sortedByFechaCreacionAsc)
                .map(ListadoTopicoDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un tópico", description = "Actualizar los detalles de un tópico existente mediante su ID.")
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO) {
        if (!id.equals((actualizarTopicoDTO.id()))) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Topico> verificarTopico = ITopicoRepository.findById(id);
        if (verificarTopico.isEmpty()) {
            throw new EntityNotFoundException("El ID " + id + " no existe.");
        }
        Topico topico = verificarTopico.get();
        topico.actualizarTopico((actualizarTopicoDTO));
        return ResponseEntity.ok(new RespuestaTopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getAutor(), topico.getCurso()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un tópico", description = "Eliminar un tópico existente mediante su ID.")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> verificarTopico = ITopicoRepository.findById(id);
        if (verificarTopico.isPresent()) {
            ITopicoRepository.delete(verificarTopico.get());
            return ResponseEntity.ok("Su topico fue eliminado");
        } else {
            return ResponseEntity.status(404).body("El ID " + id + " no existe");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tópico por ID", description = "Obtener los detalles de un tópico específico mediante su ID.")
    public ResponseEntity<ListadoTopicoDTO> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> verificarTopico = ITopicoRepository.findById(id);
        if (verificarTopico.isPresent()) {
            Topico topico = verificarTopico.get();
            ListadoTopicoDTO listadoTopicoDTO = new ListadoTopicoDTO(topico);
            return ResponseEntity.ok(listadoTopicoDTO);
        } else {
            throw new EntityNotFoundException("El ID " + id + " no existe.");
        }
    }
}
