package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.CommentBO;
import org.itzixi.pojo.vo.CommentVO;
import org.itzixi.service.ICommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseInfoProperties {
    @Resource
    private ICommentService commentService;

    @PostMapping("create")
    public GraceJSONResult create(@RequestBody CommentBO commentBO, HttpServletRequest request) {

        CommentVO commentVO = commentService.createComment(commentBO);
        return GraceJSONResult.ok(commentVO);
    }

    @PostMapping("query")
    public GraceJSONResult query(@RequestParam String friendCircleId,
                                 HttpServletRequest request) {

        List<CommentVO> commentVOList = commentService.queryAll(friendCircleId);
        return GraceJSONResult.ok(commentVOList);
    }

    @PostMapping("delete")
    public GraceJSONResult delete(@RequestParam String commentId,
                                  @RequestParam String commentUserId,
                                  @RequestParam String friendCircleId) {
        if (StringUtils.isBlank(commentUserId) ||
                StringUtils.isBlank(commentId) ||
                StringUtils.isBlank(friendCircleId)) {
            return GraceJSONResult.error();
        }
        commentService.deleteComment(commentId, commentUserId, friendCircleId);
        return GraceJSONResult.ok();
    }
}
