package org.itzixi.mapper;


import org.apache.ibatis.annotations.Param;
import org.itzixi.pojo.vo.ContactsVO;

import java.util.List;
import java.util.Map;

public interface FriendshipMapperCustom {

    public List<ContactsVO> queryMyFriends(@Param("paramMap") Map<String, Object> map);
}
