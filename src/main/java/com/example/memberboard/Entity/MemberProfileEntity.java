package com.example.memberboard.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="project_member_profile_table")
public class MemberProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    public static MemberProfileEntity toSaveMemberProfileEntity(MemberEntity memberEntity,String originalFileName, String storedFileName){
        MemberProfileEntity memberProfileEntity = new MemberProfileEntity();
        memberProfileEntity.setMemberEntity(memberEntity);
        memberProfileEntity.setOriginalFileName(originalFileName);
        memberProfileEntity.setStoredFileName(storedFileName);
        return memberProfileEntity;
    }
}
