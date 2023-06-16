package com.example.memberboard.Service;

import com.example.memberboard.DTO.MemberDTO;
import com.example.memberboard.Entity.MemberEntity;
import com.example.memberboard.Entity.MemberProfileEntity;
import com.example.memberboard.Repository.MemberProfileRepository;
import com.example.memberboard.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    public Long save(MemberDTO memberDTO) throws IOException {
        if(memberDTO.getMemberProfile()==null||memberDTO.getMemberProfile().get(0).isEmpty()){
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            return memberRepository.save(memberEntity).getId();
        }else{
            MemberEntity memberEntity = MemberEntity.toSaveEntityWithFile(memberDTO);
            MemberEntity savedentity = memberRepository.save(memberEntity);
            for(MultipartFile memberProfile: memberDTO.getMemberProfile()){
                String originalFileName=memberProfile.getOriginalFilename();
                String storedFileName=System.currentTimeMillis()+"-"+originalFileName;
                String savePath = "D:\\springboot_img\\"+storedFileName;
                memberProfile.transferTo(new File(savePath));

                MemberProfileEntity memberProfileEntity =
                        MemberProfileEntity.toSaveMemberProfileEntity(memberEntity, originalFileName, storedFileName);
                memberProfileRepository.save(memberProfileEntity);
            }
            return memberEntity.getId();
        }
    }

    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if(memberEntity.isPresent()){
            return true;
        }else{
            return false;
        }
    }


    public void loginAxios(MemberDTO memberDTO) {
        memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                        .orElseThrow(()->new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberEmail(String loginEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(()->new NoSuchElementException("something wrong"));
        return MemberDTO.toDTO((memberEntity));

    }
}
