package com.example.memberboard.Service;

import com.example.memberboard.DTO.GameDTO;
import com.example.memberboard.DTO.GameFileDTO;
import com.example.memberboard.Entity.GameEntity;
import com.example.memberboard.Entity.GameFileEntity;
import com.example.memberboard.Repository.GameFileRepository;
import com.example.memberboard.Repository.GameRepository;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameFileRepository gameFileRepository;

    public Long save(GameDTO gameDTO) throws IOException {
        if(gameDTO.getGameFile()==null || gameDTO.getGameFile().get(0).isEmpty()){
            //파일 없는 경우
            GameEntity gameEntity = GameEntity.toSaveEntity(gameDTO);
            return gameRepository.save(gameEntity).getId();
        }else{
            GameEntity fileEntity = GameEntity.toSaveEntityWithFile(gameDTO);
            GameEntity dataEntity = gameRepository.save(fileEntity);

            for(MultipartFile gameFile : gameDTO.getGameFile()){
                String originalFileName = gameFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+"_"+originalFileName;
                String savePath = "D:\\Hexagon\\"+storedFileName;
                gameFile.transferTo(new File(savePath));

                GameFileEntity gameFileEntity =
                        GameFileEntity.toSaveGameFileEntity(dataEntity, originalFileName, storedFileName);
                gameFileRepository.save(gameFileEntity);
            }
            return dataEntity.getId();
        }
    }

    public Page<GameDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 3;

        Page<GameEntity> gameEntities = null;
        if(type.equals("title")){
            gameEntities = gameRepository.findByGameTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else if(type.equals("distr")){
            gameEntities = gameRepository.findByGameDistrContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
            // 배급사로 검색
        }else if(type.equals("creater")){
            gameEntities = gameRepository.findByGameCreatorContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
            // 제작사로 검색
        }else{
            gameEntities = gameRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        }
        Page<GameDTO> gameDTOS = gameEntities.map(gameEntity -> GameDTO.builder()
                                             .id(gameEntity.getId())
                                             .gameTitle(gameEntity.getGameTitle())
                                             .gameGenre(gameEntity.getGameGenre())
                                             .gameCreator(gameEntity.getGameCreator())
                                             .gameGrade(gameEntity.getGameGrade())
                                             .createdAt(UtilClass.dateFormat(gameEntity.getCreatedAt()))
                                             .salesPrice(gameEntity.getSalesPrice())
                                             .build());
        return gameDTOS;
    }

    public GameDTO findById(Long id) {
        GameEntity gameEntity = gameRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return GameDTO.toDTO(gameEntity);
    }

    public Long update(GameDTO gameDTO) throws IOException {
        GameEntity gameEntity = gameRepository.findById(gameDTO.getId()).orElse(null);
        if (gameEntity == null) {
            return null;
        }

        // 이전에 업로드된 파일 목록을 가져옵니다.
        List<GameFileEntity> beforeGameFiles = gameEntity.getGameFileEntityList();

        // 이전 파일들을 삭제합니다.
        deleteBeforeGameFiles(beforeGameFiles);

        // 파일 업로드 수행
        if (gameDTO.getGameFile() != null && !gameDTO.getGameFile().isEmpty()) {
            for (MultipartFile gameFile : gameDTO.getGameFile()) {
                String originalFileName = gameFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\Hexagon\\" + storedFileName;
                gameFile.transferTo(new File(savePath));

                GameFileEntity gameFileEntity = GameFileEntity.toSaveGameFileEntity(gameEntity, originalFileName, storedFileName);
                gameFileRepository.save(gameFileEntity);
                gameEntity.getGameFileEntityList().add(gameFileEntity);
            }
        }
        // 필드 값 업데이트
        GameEntity updatedGameEntity = GameEntity.toUpdateWithFileEntity(gameDTO);
        gameRepository.save(updatedGameEntity);

        return updatedGameEntity.getId();
    }
//
//        if(gameDTO.getGameFile()==null || gameDTO.getGameFile().get(0).isEmpty()) {
//            //파일 없는 경우
//            System.out.println("파일없음");
//            GameEntity gameEntity = GameEntity.toUpdateEntity(gameDTO);
//            gameRepository.save(gameEntity);
//            return gameRepository.save(gameEntity).getId();
//        }else{
//            //파일 있는 경우
//            System.out.println("파일있음");
//            GameEntity fileEntity = GameEntity.toUpdateWithFileEntity(gameDTO);
//            GameEntity dataEntity = gameRepository.save(fileEntity);
//
//            for(MultipartFile gameFile : gameDTO.getGameFile()){
//                String originalFileName = gameFile.getOriginalFilename();
//                String storedFileName = System.currentTimeMillis()+"_"+originalFileName;
//                String savePath = "D:\\Hexagon\\"+storedFileName;
//                gameFile.transferTo(new File(savePath));
//
//                GameFileEntity gameFileEntity =
//                        GameFileEntity.toUpdateGameFileEntity(dataEntity, originalFileName, storedFileName);
//                gameFileRepository.save(gameFileEntity);
//            }
//            return dataEntity.getId();
//        }

    private void deleteBeforeGameFiles(List<GameFileEntity> beforeGameFiles){
        if(beforeGameFiles != null && !beforeGameFiles.isEmpty()){
            for(GameFileEntity gameFile : beforeGameFiles){
                String filePath = "D:\\Hexagon\\"+gameFile.getStoredFileName();
                File file = new File(filePath);
                if(file.exists()){
                    if(file.delete()){
                        System.out.println("파일 삭제 완료"+filePath);
                        // 데이터베이스에서 파일 정보 삭제
                        gameFileRepository.delete(gameFile);
                    }else{
                        System.out.println("파일 삭제 실패"+filePath);
                    }

                }
            }
        }
    }


    @Transactional
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
}
