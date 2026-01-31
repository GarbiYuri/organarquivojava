package com.franciscoyuri.automacaoarquivo.controller;

import com.franciscoyuri.automacaoarquivo.business.FileMovementService;
import com.franciscoyuri.automacaoarquivo.infrastructure.entitys.FileMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filemovement")
@RequiredArgsConstructor
public class FileMovementController {
    private final FileMovementService fileMovementService;

    @PostMapping
    public ResponseEntity<String> organizateFiles(@RequestParam String path){
        fileMovementService.processarDiretorio(path);
        return ResponseEntity.ok().build();
    }
}
