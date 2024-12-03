package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.CommentBO;
import org.itzixi.pojo.vo.CommentVO;
import org.itzixi.service.ICommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseInfoProperties {
    @Resource
    private ICommentService commentService;

    @PostMapping("create")
    public GraceJSONResult create(@RequestBody CommentBO commentBO,
                                  HttpServletRequest request) {

        CommentVO commentVO=commentService.createComment(commentBO);
        return GraceJSONResult.ok(commentVO);
    }
}
