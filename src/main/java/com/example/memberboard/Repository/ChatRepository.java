package com.example.memberboard.Repository;

import com.example.memberboard.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessageEntity, Long> {
    // 방의 고유 주소를 통해 채팅 이력을 모두 불러온다.
    List<ChatMessageEntity> findAllByChatRoomEntity_RoomId(String roomId);
}
