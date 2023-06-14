package com.example.memberboard.Controller;

import com.example.memberboard.DTO.BoardDTO;
import com.example.memberboard.DTO.CommentDTO;
import com.example.memberboard.Service.BoardService;
import com.example.memberboard.Service.CommentService;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping("/board/save")
    public String saveForm(){
        return "/boardPages/boardSave";
    }
    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }
    @GetMapping("/board/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "/boardPages/boardList";
    }
    @GetMapping("/board")
    public String paging(@PageableDefault(page=1) Pageable pageable,
                         @RequestParam(value="type",required=false,defaultValue = "")String type,
                         @RequestParam(value="q", required = false, defaultValue = "")String q,
                         Model model){
        Page<BoardDTO> boardDTOPage = boardService.paging(pageable, type, q);
        if(boardDTOPage.getTotalElements()==0){
            model.addAttribute("boardList", null);
        }else{
            model.addAttribute("boardList", boardDTOPage);
        }
        int blockLimit=3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOPage.getTotalPages();

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);

        return "/boardPages/boardPaging";
    }
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id,
                           @RequestParam(value="page",required=false, defaultValue ="0")int page,
                           @RequestParam("type")String type,
                           @RequestParam("q")String q,
                           Model model){
        boardService.updateHits(id);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        try{
            BoardDTO boardDTO = boardService.findById(id);
            model.addAttribute("board",boardDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(id);
            if(commentDTOList.size()>0){
                model.addAttribute("commentList", commentDTOList);
            }else{
                model.addAttribute("commentList", null);
            }
            return "/boardPages/boardDetail";
        } catch(NoSuchElementException e){
            return "/boardPages/boardNotFound";
        }
    }
    @GetMapping("/board/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable("id")Long id){
        BoardDTO boardDTO = boardService.findById(id);
        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }
    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "/boardPages/boardUpdate";
    }
    @PutMapping("/board/update/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO){
        boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
