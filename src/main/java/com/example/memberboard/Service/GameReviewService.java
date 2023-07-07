package com.example.memberboard.Service;

import com.example.memberboard.DTO.GameReviewDTO;
import com.example.memberboard.Entity.GameEntity;
import com.example.memberboard.Entity.GameReviewEntity;
import com.example.memberboard.Repository.GameRepository;
import com.example.memberboard.Repository.GameReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GameReviewService {
    private final GameReviewRepository gameReviewRepository;
    private final GameRepository gameRepository;
    @Transactional
    public Long save(GameReviewDTO gameReviewDTO){
        GameEntity gameEntity = gameRepository.findById(gameReviewDTO.getGameId()).orElseThrow(()->new NoSuchElementException());
        GameReviewEntity gameReviewEntity = GameReviewEntity.toSaveEntity(gameEntity, gameReviewDTO);
        return gameReviewRepository.save(gameReviewEntity).getId();
    }
    @Transactional
    public List<GameReviewDTO> findAll(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(()->new NoSuchElementException());
        List<GameReviewEntity> gameReviewEntityList = gameReviewRepository.findByGameEntityOrderByIdDesc(gameEntity);
        List<GameReviewDTO> gameReviewDTOList = new ArrayList<>();

        gameReviewEntityList.forEach(gameReview->{
            gameReviewDTOList.add(GameReviewDTO.toDTO(gameReview));
        });
        return gameReviewDTOList;
    }


}
