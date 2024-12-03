package org.itzixi.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.itzixi.pojo.vo.FriendCircleVO;
import org.itzixi.pojo.vo.NewFriendsVO;

import java.util.Map;

/**
 * <p>
 * 朋友圈表 Mapper 接口
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public interface FriendCircleMapperCustom {

    public Page<FriendCircleVO> queryFriendCircleList(@Param("page") Page<FriendCircleVO> page,
                                                      @Param("paramMap") Map<String,Object> map);
}
