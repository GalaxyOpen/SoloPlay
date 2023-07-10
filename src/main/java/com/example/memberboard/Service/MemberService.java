package com.example.memberboard.Service;

import com.example.memberboard.DTO.MemberDTO;
import com.example.memberboard.Entity.MemberEntity;
import com.example.memberboard.Repository.MemberProfileRepository;
import com.example.memberboard.Repository.MemberRepository;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Long save(MemberDTO memberDTO) throws IOException {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            return memberRepository.save(memberEntity).getId();
    }

    public boolean emailCheck(String memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
        if(optionalMemberEntity.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    @Transactional
    public Page<MemberDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 10;
        Page<MemberEntity> memberEntities = null;
        if(type.equals("memberId")){
            memberEntities=memberRepository.findByMemberIdContaining(q, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else if(type.equals("memberName")){
            memberEntities=memberRepository.findByMemberNameContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else{
            memberEntities=memberRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        }
        Page<MemberDTO> memberDTOS = memberEntities.map(memberEntity -> MemberDTO.builder()
                .id(memberEntity.getId())
                .memberId(memberEntity.getMemberId())
                .memberName(memberEntity.getMemberName())
                .memberMobile(memberEntity.getMemberMobile())
                .createdAt(UtilClass.dateFormat(memberEntity.getCreatedAt()))
                .build());
        return memberDTOS;
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
        System.out.println("memberEntity = " + memberEntity);
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberId(String loginId) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberId(loginId);
        MemberEntity memberEntity = memberEntityOptional.orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }
}
