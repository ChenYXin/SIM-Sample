package org.itzixi.pojo.bo;

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
public class FriendCircleBO implements Serializable {
    private String userId;
    private String words;
    private String images;
    private String video;
    private LocalDateTime publishTime;
}
