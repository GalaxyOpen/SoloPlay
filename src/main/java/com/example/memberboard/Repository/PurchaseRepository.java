package com.example.memberboard.Repository;

import com.example.memberboard.Entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository <PurchaseEntity, Long> {


}
