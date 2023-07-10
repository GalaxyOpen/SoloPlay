package com.example.memberboard.Controller;

import com.example.memberboard.DTO.GameReviewDTO;
import com.example.memberboard.Service.GameReviewService;
import com.example.memberboard.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameReviewController {
    private final GameReviewService gameReviewService;
    @PostMapping("/review/save")
    public ResponseEntity save(@RequestBody GameReviewDTO gameReviewDTO){
        try{
            gameReviewService.save(gameReviewDTO);
            List<GameReviewDTO> gameReviewDTOList = gameReviewService.findAll(gameReviewDTO.getGameId());
            System.out.println("gameReviewDTOList = " + gameReviewDTOList);
            return new ResponseEntity<>(gameReviewDTOList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
