package com.example.memberboard.Entity;

import com.example.memberboard.DTO.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="project_member_table")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 30, unique = true)
    private String memberId;

    @Column(length = 150, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 50, nullable = false)
    private String memberEmail;

    @Column(length=20, nullable = false)
    private String memberMobile;

    @Column(nullable = false)
    private String memberBirth;

    @Column(nullable = false)
    private Long totalPoint;


    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<GameEntity> gameEntityList = new ArrayList<>();

    @OneToMany(mappedBy="memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<BucketEntity> bucketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointEntity> pointEntityList = new ArrayList<>();

    @OneToMany(mappedBy="memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<GameReviewEntity> gameReviewEntityList = new ArrayList<>();

    @OneToMany(mappedBy ="memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<PurchaseEntity> purchaseEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SalesEntity> salesEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<StarEntity> starEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<ChatRoomEntity> chatRoomEntityList = new ArrayList<>();
    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setTotalPoint(1000L);
        return memberEntity;
       }
    public static MemberEntity toUpdateEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setTotalPoint(memberDTO.getTotalPoint());
        return memberEntity;
    }
//    public static MemberEntity toSaveEntityWithFile(MemberDTO memberDTO){
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
//        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
//        memberEntity.setMemberName(memberDTO.getMemberName());
//        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
//        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
//        memberEntity.setFileAttached(1);
//        return memberEntity;
//    }
//    public static MemberEntity toUpdateEntityWithFile(MemberDTO memberDTO){
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setId(memberEntity.getId());
//        memberEntity.setMemberEmail(memberEntity.getMemberEmail());
//        memberEntity.setMemberPassword(memberEntity.getMemberPassword());
//        memberEntity.setMemberName(memberEntity.getMemberName());
//        memberEntity.setMemberBirth(memberEntity.getMemberBirth());
//        memberEntity.setMemberMobile(memberEntity.getMemberMobile());
//        return memberEntity;
//    } 추후 수정
}
