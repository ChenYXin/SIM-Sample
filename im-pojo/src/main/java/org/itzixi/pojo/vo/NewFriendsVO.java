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
public class NewFriendsVO implements Serializable {
    private String friendRequestId;
    private String myFriendId;
    private String myFriendFace;
    private String myFriendNickname;
    private String verifyMessage;
    private LocalDateTime requestTime;
    private Integer verifyStatus;
    private boolean isTouched = false;
}
