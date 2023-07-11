package com.example.memberboard.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSaveDTO {
    private Long id;
    private Long memberId;
    private Long gameId;
}
