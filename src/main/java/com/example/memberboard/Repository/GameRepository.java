package com.example.memberboard.Repository;

import com.example.memberboard.Entity.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    Page<GameEntity> findByGameTitleContaining(String q, PageRequest id);

    Page<GameEntity> findByGameDistrContaining(String q, PageRequest id);

    Page<GameEntity> findByGameCreatorContaining(String q, PageRequest id);
}
