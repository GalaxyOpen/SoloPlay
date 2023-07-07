package com.example.memberboard.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AutoCloseable.class)
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable=false, nullable = false)
    private LocalDateTime createdAt;
    // 작성일

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    // 수정일
}
