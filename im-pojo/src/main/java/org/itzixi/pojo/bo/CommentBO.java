package org.itzixi.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentBO implements Serializable {

    private String belongUserId;
    private String friendCircleId;

    private String fatherId;

    private String commentUserId;
    private String commentContent;

}
