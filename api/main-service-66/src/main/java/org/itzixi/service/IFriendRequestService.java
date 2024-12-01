package org.itzixi.service;

import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.RequestParam;

public interface IFriendRequestService {

    public void addNewRequest(NewFriendRequestBO friendRequestBO);

    public PagedGridResult queryNewFriendList(String userId,
                                              Integer page,
                                              Integer pageSize);

    public void passNewFriend( String friendRequestId,
                                String friendRemark);
}
