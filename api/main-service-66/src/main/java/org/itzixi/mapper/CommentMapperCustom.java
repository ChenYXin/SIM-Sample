package org.itzixi.mapper;

import org.apache.ibatis.annotations.Param;
import org.itzixi.pojo.vo.CommentVO;

import java.util.List;
import java.util.Map;

public interface CommentMapperCustom  {

    public List<CommentVO> queryFriendCircleComments(@Param("paramMap") Map<String,Object> map);
}
