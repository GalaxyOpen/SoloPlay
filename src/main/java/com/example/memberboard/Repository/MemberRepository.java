package com.example.memberboard.Repository;

import com.example.memberboard.Entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    Optional<MemberEntity> findByMemberId(String loginId);

    Page<MemberEntity> findByMemberIdContaining(String q, PageRequest id);

    Page<MemberEntity> findByMemberNameContaining(String q, PageRequest id);
}
