package com.example.memberboard.DTO;

import lombok.Data;

@Data
public class GameFileDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long gameId;

}
