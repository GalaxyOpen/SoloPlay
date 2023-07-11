package com.example.memberboard.Controller;

import com.example.memberboard.DTO.BucketDetailDTO;
import com.example.memberboard.DTO.BucketSaveDTO;
import com.example.memberboard.DTO.MemberDTO;
import com.example.memberboard.Service.BucketService;
import com.example.memberboard.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;
    private final MemberService memberService;

    //장바구니 담기
    @PostMapping("/save")
    public @ResponseBody boolean save(@ModelAttribute BucketSaveDTO bucketSaveDTO){
        // 리스트에 gameId, memberId 확인
        // 없으면 저장이후 True 리턴
        // 있으면 false 리턴
        if(bucketService.check(bucketSaveDTO)){
            bucketService.save(bucketSaveDTO);
            return true;
        }else{
            return false;
        }
    }
    @GetMapping("/{memberId}")
    public String findByMemberId(@PathVariable("memberId")Long memberId, Model model){
        List<BucketDetailDTO> bucketList = bucketService.findByMemberId(memberId);
        boolean bucket = false;
        if(!bucketList.isEmpty()){
            int totalPrice=0;
            for(BucketDetailDTO b : bucketList){
                totalPrice += b.getBuyAmount();
            }
            model.addAttribute("bucketList", bucketList);
            model.addAttribute("totalPrice", totalPrice);

            MemberDTO memberDTO = memberService.findById(memberId);
            Long totalPoint = memberDTO.getTotalPoint();
            model.addAttribute("totalPoint", totalPoint);
        }else{
            //비어있을때
            bucket = true;
        }
        model.addAttribute("bucket", bucket);
        return "/gamePages/gameBucket";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id){
        bucketService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
