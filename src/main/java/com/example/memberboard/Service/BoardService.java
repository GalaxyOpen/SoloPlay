package com.example.memberboard.Service;

import com.example.memberboard.DTO.BoardDTO;
import com.example.memberboard.Entity.BoardEntity;
import com.example.memberboard.Entity.BoardFileEntity;
import com.example.memberboard.Repository.BoardFileRepository;
import com.example.memberboard.Repository.BoardRepository;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException{
        if(boardDTO.getBoardFile()==null || boardDTO.getBoardFile().get(0).isEmpty()){
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();
        }else{
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(boardDTO);
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            for(MultipartFile boardFile : boardDTO.getBoardFile()){
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+"-"+originalFileName;
                String savePath = "D:\\springboot_img\\"+storedFileName;
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFileEntity(savedEntity, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return savedEntity.getId();
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        boardEntityList.forEach(boardEntity -> {
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        });
        return boardDTOList;
    }
    @Transactional
    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }

    public Page<BoardDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = null;
        if(type.equals("title")){
            boardEntities= boardRepository.findByBoardTitleContaining(q, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else if(type.equals("writer")){
            boardEntities=boardRepository.findByBoardWriterContaining(q,PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        }else{
            boardEntities = boardRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        }
        Page<BoardDTO> boardDTOS = boardEntities.map(boardEntity -> BoardDTO.builder()
                .id(boardEntity.getId())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContents(boardEntity.getBoardContents())
                .createdAt(UtilClass.dateFormat(boardEntity.getCreatedAt()))
                .boardHits(boardEntity.getBoardHits())
                .build());
        return boardDTOS;
    }
    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity=boardRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}

