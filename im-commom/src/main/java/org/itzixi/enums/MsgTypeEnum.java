package org.itzixi.enums;

/**
 * 
 * @Description: 发送消息的类型/动作 枚举
 */
public enum MsgTypeEnum {
	
	CONNECT_INIT(0, "第一次(或重连)初始化连接"),
	WORDS(1, "文字表情消息"),
	IMAGE(2, "图片"),
	VOICE(3, "语音"),
	VIDEO(4, "视频"),



	SIGNED(8, "消息签收"),
	KEEPALIVE(9, "客户端保持心跳"),
	heart(10, "拉取好友");
	
	public final Integer type;
	public final String content;
	
	MsgTypeEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
