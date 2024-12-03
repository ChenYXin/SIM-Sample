package org.itzixi.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.itzixi.pojo.Comment;
import org.itzixi.pojo.FriendCircleLiked;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendCircleVO implements Serializable {

    private String friendCircleId;
    private String userId;
    private String userNickname;
    private String userFace;
    private String words;
    private String images;
    private LocalDateTime publishTime;

    private List<FriendCircleLiked> likedFriends = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

}
