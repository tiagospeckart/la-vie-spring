package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.PsychologistController;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.services.PsychologistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/psychologist")
@Tag(name = "Psychologist Management", description = "Endpoints for managing psychologists")
public class PsychologistControllerImpl implements PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping("/{id}")
    @Operation(summary = "Find Psychologist by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Psychologist found",
                            content = @Content(schema = @Schema(implementation = PsychologistDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Psychologist not found")
            })
    @Override
    public ResponseEntity<PsychologistDTO> findById(@Parameter(description = "ID of psychologist to be searched") @PathVariable Integer id) {
        PsychologistDTO psychologistDTO = psychologistService.findById(id);
        if (psychologistDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(psychologistDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Psychologist",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Psychologist created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<PsychologistDTO> create(@Parameter(description = "Psychologist to add") @RequestBody PsychologistDTO psychologistDTO) {
        PsychologistDTO createdPsychologist = psychologistService.create(psychologistDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPsychologist.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdPsychologist);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Psychologist",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Psychologist not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<PsychologistDTO> updateById(@Parameter(description = "ID of psychologist to be updated") @PathVariable Integer id,
                                                      @Parameter(description = "Updated psychologist") @RequestBody PsychologistDTO updatedPsychologist) {
        updatedPsychologist.setId(id);
        PsychologistDTO savedPsychologist = psychologistService.update(updatedPsychologist);
        if (savedPsychologist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedPsychologist);
    }

    @GetMapping
    @Operation(summary = "List all Psychologists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    @Override
    public ResponseEntity<List<PsychologistDTO>> listAll() {
        List<PsychologistDTO> allPsychologists = psychologistService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allPsychologists.size()));
        return ResponseEntity.ok().headers(headers).body(allPsychologists);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Psychologist by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Psychologist not found")
            })
    @Override
    public ResponseEntity<Void> deleteById(@Parameter(description = "ID of psychologist to be deleted") @PathVariable Integer id) {
        psychologistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
