package com.example.memberboard.DTO;

import com.example.memberboard.Entity.PurchaseEntity;
import com.example.memberboard.UtilClass.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailDTO {
    private Long id;
    private Long memberId; // 회원번호
    private Long gameId; // 게임번호

    // 장바구니에서 보여줄 것
    private String createdAt; // 구매일자
    private int buyAmount; // 결제금액
    private String payMethod; // 결제방법
    private String status; // 구매 상태(완료, 취소, 환불등)

    public static PurchaseDetailDTO toPurchaseDetailDTO(PurchaseEntity purchaseEntity){
        PurchaseDetailDTO purchaseDetailDTO = new PurchaseDetailDTO();

        purchaseDetailDTO.setId(purchaseEntity.getId());
        purchaseDetailDTO.setMemberId(purchaseEntity.getMemberEntity().getId());
        purchaseDetailDTO.setGameId(purchaseEntity.getGameEntity().getId());

        purchaseDetailDTO.setCreatedAt(UtilClass.dateFormat(purchaseEntity.getCreatedAt()));
        purchaseDetailDTO.setBuyAmount(purchaseEntity.getBuyAmount());
        purchaseDetailDTO.setPayMethod(purchaseEntity.getPayMethod());
        purchaseDetailDTO.setStatus(purchaseEntity.getStatus());

        return purchaseDetailDTO;
    }
}
