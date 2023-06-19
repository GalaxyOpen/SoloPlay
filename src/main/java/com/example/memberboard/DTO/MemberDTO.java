package com.example.memberboard.DTO;

import com.example.memberboard.Entity.MemberEntity;
import com.example.memberboard.Entity.MemberProfileEntity;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberBirth;
    private String createdAt;
    private int fileAttached;
    private List<MultipartFile> memberProfile;
    private List<String> originalFileName;
    private List<String> storedFileName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setCreatedAt(UtilClass.dateFormat(memberEntity.getCreatedAt()));

//        if(memberEntity.getFileAttached()==1){
//            memberDTO.setFileAttached(1);
//            List<String> originalFileNameList = new ArrayList<>();
//            List<String> storedFileNameList = new ArrayList<>();
//
//            for(MemberProfileEntity memberProfileEntity : memberEntity.getMemberProfileEntityList()){
//                originalFileNameList.add(memberProfileEntity.getOriginalFileName());
//                storedFileNameList.add(memberProfileEntity.getStoredFileName());
//            }
//            memberDTO.setOriginalFileName(originalFileNameList);
//            memberDTO.setStoredFileName(storedFileNameList);
//        }else{
//            memberDTO.setFileAttached(0);
//        }
        return memberDTO;

    }
}
