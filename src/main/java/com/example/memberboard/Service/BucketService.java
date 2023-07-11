package com.example.memberboard.Service;

import com.example.memberboard.DTO.BucketDetailDTO;
import com.example.memberboard.DTO.BucketSaveDTO;
import com.example.memberboard.Entity.BucketEntity;
import com.example.memberboard.Entity.GameEntity;
import com.example.memberboard.Entity.MemberEntity;
import com.example.memberboard.Repository.BucketRepository;
import com.example.memberboard.Repository.GameRepository;
import com.example.memberboard.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;
    public boolean check(BucketSaveDTO bucketSaveDTO) {
        Optional<BucketEntity> check = bucketRepository.findByMemberEntity_IdAndGameEntity_Id(bucketSaveDTO.getMemberId(), bucketSaveDTO.getGameId());
        if(check.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public Long save(BucketSaveDTO bucketSaveDTO) {
        MemberEntity memberEntity = memberRepository.findById(bucketSaveDTO.getMemberId()).get();
        GameEntity gameEntity = gameRepository.findById(bucketSaveDTO.getGameId()).get();
        return bucketRepository.save(BucketEntity.toBucketSaveEntity(memberEntity,gameEntity)).getId();
    }

    public List<BucketDetailDTO> findByMemberId(Long memberId) {
        List<BucketEntity> bucketEntityList = bucketRepository.findByMemberEntity_Id(memberId);
        List<BucketDetailDTO> bucketList = new ArrayList<>();
        for(BucketEntity b : bucketEntityList){
            bucketList.add(BucketDetailDTO.toBucketDetailDTO(b));
        }
        return bucketList;
    }

    public void deleteById(Long id) {
        bucketRepository.deleteById(id);
    }
}
