package org.itzixi.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLoginBO {
    private String mobile;
    private String smsCode;
    private String nickName;

}
