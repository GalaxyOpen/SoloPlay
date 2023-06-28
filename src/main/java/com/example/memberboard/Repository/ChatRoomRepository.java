package com.example.memberboard.Repository;

import com.example.memberboard.Entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    //방 주소를 통해서 ChatRoomEntity 를 가져옴.
    ChatRoomEntity findByRoomId(String roomId);
}
