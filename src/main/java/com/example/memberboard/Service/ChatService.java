package com.example.memberboard.Service;

import com.example.memberboard.DTO.ChatMessageDetailDTO;
import com.example.memberboard.DTO.ChatRoomDetailDTO;


import java.util.List;


public interface ChatService {

        List<ChatRoomDetailDTO> findAllRooms();
        ChatRoomDetailDTO findRoomById(String id);

        //채팅방 생성하기
        void createChatRoomDTO(String name, int password, String chatMentor);
        void deleteById(Long chatRoomId);

        List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId);

}
