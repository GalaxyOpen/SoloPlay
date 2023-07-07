package com.example.memberboard.Repository;

import com.example.memberboard.Entity.GameFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameFileRepository extends JpaRepository<GameFileEntity, Long> {
}
