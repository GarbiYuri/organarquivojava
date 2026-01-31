package com.franciscoyuri.automacaoarquivo.infrastructure.repository;

import com.franciscoyuri.automacaoarquivo.infrastructure.entitys.FileMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileMovementRepository extends JpaRepository<FileMovement, Long> {

}
