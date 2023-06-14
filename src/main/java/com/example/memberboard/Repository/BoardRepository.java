package com.example.memberboard.Repository;

import com.example.memberboard.Entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findByBoardTitleContaining(String q, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContaining(String q, Pageable pageable);
}
