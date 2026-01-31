package com.franciscoyuri.automacaoarquivo.business;

import com.franciscoyuri.automacaoarquivo.infrastructure.entitys.FileMovement;
import com.franciscoyuri.automacaoarquivo.infrastructure.repository.FileMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileMovementService {
    private final FileMovementRepository repository;

    private String detectarTipo(String name){
        String lower = name.toLowerCase();

        if (lower.endsWith(".pdf")) return "PDF";
        if (lower.endsWith(".jpg") || lower.endsWith(".png") || lower.endsWith(".jpeg")) return "IMAGE";
        if (lower.endsWith(".zip") || lower.endsWith(".rar") || lower.endsWith(".7z")) return "ZIP";
        return "OTHER";
    }

    private String destinoPorTipo(String tipo) {
        return switch (tipo) {
            case "PDF" -> "/home/francisco/Downloads/Automacao/PDF";
            case "IMAGE" -> "/home/francisco/Downloads/Automacao/Imagens";
            case "ZIP" -> "/home/francisco/Downloads/Automacao/Compactados";
            default -> "/home/francisco/Downloads/Automacao/Outros";
        };
    }
    public  void processarDiretorio(String path){
        File pasta = new File(path);
        if (!pasta.exists() || !pasta.isDirectory()){
            return;
        }

        File[] arquivos = pasta.listFiles();
        if (arquivos == null)return;
        for (File arquivo : arquivos) {
            if (!arquivo.isFile()) continue;

            String tipo = detectarTipo(arquivo.getName());
            String pastaDestinoStr = destinoPorTipo(tipo);

            Path pastaDestino = Paths.get(pastaDestinoStr);
            Path origem = arquivo.toPath();
            Path destino = pastaDestino.resolve(arquivo.getName());

            FileMovement movement = FileMovement.builder()
                    .name(arquivo.getName())
                    .type(tipo)
                    .originalPath(origem.toString())
                    .newPath(destino.toString())
                    .build();

            try {
                Files.createDirectories(pastaDestino);
                Files.move(origem, destino, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            repository.save(movement);

        }
        }
    }

