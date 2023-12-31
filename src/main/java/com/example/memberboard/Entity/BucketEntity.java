package com.example.memberboard.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bucket_table")
@Getter
@Setter

public class BucketEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    @Column(nullable = false)
    private int buyAmount;

    @Column(length = 20, nullable = false)
    private String payMethod;

    @Column(length = 20, nullable = false)
    private String status;

    public static BucketEntity toBucketSaveEntity(MemberEntity memberEntity, GameEntity gameEntity){
        BucketEntity bucketEntity = new BucketEntity();
        bucketEntity.setMemberEntity(memberEntity);
        bucketEntity.setGameEntity(gameEntity);
        return bucketEntity;
    }
}
