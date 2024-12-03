package org.itzixi.service.impl;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.CommentMapper;
import org.itzixi.mapper.CommentMapperCustom;
import org.itzixi.pojo.Comment;
import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.CommentBO;
import org.itzixi.pojo.vo.CommentVO;
import org.itzixi.service.ICommentService;
import org.itzixi.service.IUsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends BaseInfoProperties implements ICommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private IUsersService usersService;
    @Autowired
    private CommentMapperCustom commentMapperCustom;

    @Transactional
    @Override
    public CommentVO createComment(CommentBO commentBO) {
        //新增评论
        Comment pendingComment = new Comment();
        BeanUtils.copyProperties(commentBO, pendingComment);
        pendingComment.setCreatedTime(LocalDateTime.now());

        commentMapper.insert(pendingComment);
        //留言后的最新评论数据需要返回给前端（提供前端做的扩展数据）
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(pendingComment, commentVO);

        Users users = usersService.getById(commentBO.getCommentUserId());

        commentVO.setCommentUserNickname(users.getNickname());
        commentVO.setCommentUserFace(users.getFace());
        commentVO.setCommentId(pendingComment.getId());

        return commentVO;
    }

    @Override
    public List<CommentVO> queryAll(String friendCircleId) {
        Map<String,Object> map=new HashMap<>();
        map.put("friendCircleId",friendCircleId);

        return commentMapperCustom.queryFriendCircleComments(map);
    }
}
