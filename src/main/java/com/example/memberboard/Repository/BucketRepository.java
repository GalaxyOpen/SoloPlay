package com.example.memberboard.Repository;

import com.example.memberboard.DTO.BucketSaveDTO;
import com.example.memberboard.Entity.BucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<BucketEntity, Long> {

    Optional<BucketEntity> findByMemberEntity_IdAndGameEntity_Id(Long memberId, Long gameId);

    List<BucketEntity> findByMemberEntity_Id(Long memberId);
    void deleteAllByMemberEntity_Id(String memberId);
}
