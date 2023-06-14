package com.example.memberboard.Repository;

import com.example.memberboard.Entity.MemberProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
}
