package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.PsychologistController;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.service.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/psychologist")
public class PsychologistControllerImpl implements PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PsychologistDTO> findById(@PathVariable Integer id) {
        PsychologistDTO psychologistDTO = psychologistService.findById(id);
        if (psychologistDTO == null) {
            return ResponseEntity.notFound().build();  // Returning a 404 if not found
        }
        return ResponseEntity.ok(psychologistDTO);
    }

    @PostMapping
    @Override
    public ResponseEntity<PsychologistDTO> create(@RequestBody PsychologistDTO psychologistDTO) {
        PsychologistDTO createdPsychologist = psychologistService.create(psychologistDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPsychologist.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdPsychologist);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<PsychologistDTO> updateById(@PathVariable Integer id, @RequestBody PsychologistDTO updatedPsychologist) {
        updatedPsychologist.setId(id);
        PsychologistDTO savedPsychologist = psychologistService.update(updatedPsychologist);
        if (savedPsychologist == null) {
            return ResponseEntity.notFound().build();  // Returning a 404 if update operation failed
        }
        return ResponseEntity.ok(savedPsychologist);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<PsychologistDTO>> listAll() {
        List<PsychologistDTO> allPsychologists = psychologistService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allPsychologists.size()));
        return ResponseEntity.ok().headers(headers).body(allPsychologists);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        psychologistService.deleteById(id);
        return ResponseEntity.noContent().build();  // Returning a 204, no content if delete is successful
    }
}