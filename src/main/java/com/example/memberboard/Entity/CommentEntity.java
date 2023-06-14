package com.example.memberboard.Entity;

import com.example.memberboard.DTO.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="project_comment_table")
public class CommentEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=20, nullable=false)
    private String commentWriter;

    @Column(length=500, nullable = false)
    private String commentContents;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="boardId")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(BoardEntity boardEntity, CommentDTO commentDTO){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }


}
