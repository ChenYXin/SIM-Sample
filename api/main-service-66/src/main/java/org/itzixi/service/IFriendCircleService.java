package org.itzixi.service;

import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.RequestParam;

public interface IFriendCircleService {

    public void publish(FriendCircleBO friendCircleBO);

    public PagedGridResult queryList(String userId,
                                     Integer page,
                                     Integer pageSize);
}
