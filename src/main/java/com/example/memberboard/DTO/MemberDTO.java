package com.example.memberboard.DTO;

import com.example.memberboard.Entity.MemberEntity;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MemberDTO {
    private Long id;
    private String memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberBirth;
    private String createdAt;
    private Long totalPoint;

    public static MemberDTO toDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setTotalPoint(memberEntity.getTotalPoint());
        memberDTO.setCreatedAt(UtilClass.dateFormat(memberEntity.getCreatedAt()));
        return memberDTO;

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

    }
}
