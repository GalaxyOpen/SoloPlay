package com.example.memberboard.Controller;

import com.example.memberboard.DTO.GameDTO;
import com.example.memberboard.DTO.GameReviewDTO;
import com.example.memberboard.DTO.MemberDTO;
import com.example.memberboard.Service.GameReviewService;
import com.example.memberboard.Service.GameService;
import com.example.memberboard.Service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final MemberService memberService;
    private final GameReviewService gameReviewService;

    @GetMapping("/game/save")
    public String saveForm(){
        return "/gamePages/gameSave";
    }
    @PostMapping("/game/save")
    public String save(@ModelAttribute GameDTO gameDTO) throws IOException {
       gameService.save(gameDTO);
       return "redirect:/game";
    }
    @Transactional
    @GetMapping("/game")
    public String findAll(@PageableDefault(page=1) Pageable pageable,
                          @RequestParam(value="type", required = false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model){
        Page<GameDTO> gameDTOS = gameService.paging(pageable, type, q);
        if(gameDTOS.getTotalElements()==0){
            model.addAttribute("gameList",null);
        }else{
            model.addAttribute("gameList", gameDTOS);
        }
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < gameDTOS.getTotalPages()) ? startPage + blockLimit - 1 : gameDTOS.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/gamePages/gamePagingList";
    }
    @Transactional
    @GetMapping("/game/{id}")
    public String findById(@PathVariable Long id,
                           @RequestParam(value="page", required = false, defaultValue = "0") int page,
                           @RequestParam("type") String type,
                           @RequestParam("q") String q,
                           Model model){
        model.addAttribute("page",page);
        model.addAttribute("type",type);
        model.addAttribute("q",q);

            GameDTO gameDTO = gameService.findById(id);
            MemberDTO memberDTO = memberService.findById(id);
            GameReviewDTO gameReviewDTO = gameReviewService.findById(id);

            model.addAttribute("game", gameDTO);
            model.addAttribute("member", memberDTO);
            model.addAttribute("gameReview", gameReviewDTO);

        System.out.println("gameReviewDTO = " + gameReviewDTO);
            List<GameReviewDTO> gameReviewDTOList = gameReviewService.findAll(id);
            if(gameReviewDTOList.size() > 0) {
                model.addAttribute("gameReviewList", gameReviewDTOList);
            }else{
                model.addAttribute("gameReviewList", null);
            }
            return "/gamePages/gameDetail";

    }

    @Transactional
    @GetMapping("/game/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        GameDTO gameDTO = gameService.findById(id);
        model.addAttribute("game", gameDTO);
        return "/gamePages/gameUpdate";
    }

    @Transactional
    @PostMapping("/game/update/{id}")
    public String update(@ModelAttribute GameDTO gameDTO) throws IOException{
        gameService.update(gameDTO);
        return "redirect:/game";
    }

    @GetMapping("/game/delete/{id}")
    public String delete(@PathVariable Long id){
        gameService.delete(id);
        return "redirect:/game";
    }
}
