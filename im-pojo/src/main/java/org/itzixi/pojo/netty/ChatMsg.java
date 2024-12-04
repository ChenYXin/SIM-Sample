package org.itzixi.pojo.netty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMsg {

	private String senderId;				// 发送者的用户id
	private String receiverId;				// 接受者的用户id
	private String msg;						// 聊天内容
	private Integer msgType;				// 消息类型，见枚举 MsgTypeEnum.java
	private String msgId;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime chatTime;			// 消息的聊天时间，既是发送者的发送时间、又是接受者的接受时间
	private Integer showMsgDateTimeFlag;	// 标记存储数据库，用于历史展示。每超过1分钟，则显示聊天时间，前端可以控制时间长短

	private String videoPath;				// 视频地址
	private Integer videoWidth;				// 视频宽度
	private Integer videoHeight;			// 视频高度
	private Integer videoTimes;				// 视频时间

	private String voicePath;				// 语音地址
	private Integer speakVoiceDuration;		// 语音时长
	private Boolean isRead;					// 语音消息标记是否已读未读，true: 已读，false: 未读

	private Integer communicationType;		// 聊天类型， 1:单聊，2:群聊

	private Boolean isReceiverOnLine;		// 用于标记当前接受者是否在线
}
