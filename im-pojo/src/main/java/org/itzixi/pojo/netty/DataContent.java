package org.itzixi.pojo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataContent {

//	private Integer action;			// 动作类型
	private ChatMsg chatMsg;		// 用户的聊天内容entity
	private String chatTime;		// 格式化后的聊天时间
	private String extend;			// 扩展字段
	
}
