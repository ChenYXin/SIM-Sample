package org.itzixi.service;

import org.itzixi.pojo.bo.CommentBO;
import org.itzixi.pojo.vo.CommentVO;

public interface ICommentService {

    public CommentVO createComment(CommentBO commentBO);
}
