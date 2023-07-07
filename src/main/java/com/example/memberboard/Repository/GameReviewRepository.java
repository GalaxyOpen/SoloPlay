package com.example.memberboard.Repository;

import com.example.memberboard.Entity.GameEntity;
import com.example.memberboard.Entity.GameReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameReviewRepository extends JpaRepository<GameReviewEntity, Long> {
    List<GameReviewEntity> findByGameEntityOrderByIdDesc(GameEntity gameEntity);
}
