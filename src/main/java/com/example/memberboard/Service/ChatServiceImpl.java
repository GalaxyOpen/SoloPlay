package com.example.memberboard.Service;

import com.example.memberboard.DTO.ChatMessageDetailDTO;
import com.example.memberboard.DTO.ChatRoomDTO;
import com.example.memberboard.DTO.ChatRoomDetailDTO;
import com.example.memberboard.Entity.ChatMessageEntity;
import com.example.memberboard.Entity.ChatRoomEntity;
import com.example.memberboard.Repository.ChatRepository;
import com.example.memberboard.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    //채팅방 리스트 보기
    @Override
    public List<ChatRoomDetailDTO> findAllRooms(){
        List<ChatRoomEntity> chatRoomEntityList = chatRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ChatRoomDetailDTO> chatRoomList = new ArrayList<>();

        for(ChatRoomEntity c : chatRoomEntityList){
            chatRoomList.add(ChatRoomDetailDTO.toChatRoomDetailDTO(c));
        }
        return chatRoomList;
    }
    //채팅방 ID로 채팅방 찾기
    @Override
    public ChatRoomDetailDTO findRoomById(String roomId){
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(roomId);
        ChatRoomDetailDTO chatRoomDetailDTO = ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoomEntity);
        return chatRoomDetailDTO;
    }
    // 채팅방 생성하기
    @Override
    public void createChatRoomDTO(String name, int password, String chatMentor){
        ChatRoomDTO room = ChatRoomDTO.create(name);
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toChatRoomEntity(room.getName(),room.getRoomId());
        chatRoomRepository.save(chatRoomEntity);
    }
    // 채팅방 지우기
    @Override
    public void deleteById(Long chatRoomId){
        chatRoomRepository.deleteById(chatRoomId);
    }
    // 모든 채팅 보여주기
    @Override
    public List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId){
        List<ChatMessageEntity> chatMessageEntityList = chatRepository.findAllByChatRoomEntity_RoomId(roomId);
        List<ChatMessageDetailDTO> chatMessageList = new ArrayList<>();
        for(ChatMessageEntity c:chatMessageEntityList){
            chatMessageList.add(ChatMessageDetailDTO.toChatMessageDetailDTO(c));
        }
        return chatMessageList;
    }
}
