package com.example.memberboard.DTO;

import com.example.memberboard.Entity.ChatRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {
    private Long chatRoomId;
    private String chatMentor;
    private String roomId;
    private String name;
    private int password;

    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoomEntity chatRoomEntity) {
        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();

        chatRoomDetailDTO.setChatRoomId(chatRoomEntity.getId());
        chatRoomDetailDTO.setChatMentor(chatRoomEntity.getChatMentor());
        chatRoomDetailDTO.setRoomId(chatRoomEntity.getRoomId());
        chatRoomDetailDTO.setName(chatRoomEntity.getRoomName());
        chatRoomDetailDTO.setPassword(chatRoomEntity.getRoomPassword());

        return chatRoomDetailDTO;
    }
}
