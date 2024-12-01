package org.itzixi.pojo.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * BO 相当于请求体 Query
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewFriendRequestBO {

    @NotBlank
    private String myId;
    @NotBlank
    private String friendId;
    @NotBlank
    private String verifyMessage;
    private String friendRemark;


}
