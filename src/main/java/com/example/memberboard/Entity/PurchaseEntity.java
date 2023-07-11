package com.example.memberboard.Entity;

import com.example.memberboard.DTO.PurchaseDetailDTO;
import com.example.memberboard.DTO.PurchaseSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "purchase_table")
@Getter
@Setter
public class PurchaseEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    @Column(nullable = false)
    private int buyAmount;

    @Column(length = 20, nullable = false)
    private String payMethod;

    @Column(length = 20, nullable = false)
    private String status;

public static PurchaseEntity toPurchaseEntity(MemberEntity memberEntity, GameEntity gameEntity){
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setMemberEntity(memberEntity);
    purchaseEntity.setGameEntity(gameEntity);
    purchaseEntity.setBuyAmount(purchaseEntity.getBuyAmount());
    purchaseEntity.setPayMethod(purchaseEntity.getPayMethod());
    purchaseEntity.setStatus(purchaseEntity.getStatus());
    return purchaseEntity;
    }
}
