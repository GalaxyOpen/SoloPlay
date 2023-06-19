package com.example.memberboard.Entity;

import com.example.memberboard.DTO.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="project_member_table")
public class MemberEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=30, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column(length = 20)
    private String memberName;

    @Column(length = 13)
    private String memberMobile;

    @Column
    private String memberBirth;

    @Column
    private String memberProfile;

    @Column
    private int fileAttached;


    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();

    @OneToMany(mappedBy="boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setFileAttached(0);
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
        memberEntity.setFileAttached(0);
        return memberEntity;
    }
    public static MemberEntity toSaveEntityWithFile(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setFileAttached(1);
        return memberEntity;
    }
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
