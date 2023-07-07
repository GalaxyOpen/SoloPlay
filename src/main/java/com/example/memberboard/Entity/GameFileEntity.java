package com.example.memberboard.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "game_file_table")
public class GameFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String originalFileName;

    @Column(length = 100, nullable = false)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    public static GameFileEntity toSaveGameFileEntity(GameEntity gameEntity, String originalFileName, String storedFileName){
        GameFileEntity gameFileEntity = new GameFileEntity();
        gameFileEntity.setGameEntity(gameEntity);
        gameFileEntity.setOriginalFileName(originalFileName);
        gameFileEntity.setStoredFileName(storedFileName);
        return gameFileEntity;
    }

}
