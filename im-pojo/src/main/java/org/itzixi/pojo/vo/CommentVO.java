package org.itzixi.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO implements Serializable {

    private String commentId;
    private String belongUserId;
    private String friendCircleId;

    private String fatherId;
    private String commentUserId;
    private String commentUserNickname;
    private String commentUserFace;
    private String commentContent;

    private String replyUserNickname;

    private LocalDateTime createdTime;

}
