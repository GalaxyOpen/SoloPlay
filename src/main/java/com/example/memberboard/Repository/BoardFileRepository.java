package com.example.memberboard.Repository;

import com.example.memberboard.Entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

}
