package org.itzixi.service;

import org.itzixi.pojo.bo.CommentBO;
import org.itzixi.pojo.vo.CommentVO;

import java.util.List;

public interface ICommentService {

    public CommentVO createComment(CommentBO commentBO);

    public List<CommentVO> queryAll(String friendCircleId);
}
