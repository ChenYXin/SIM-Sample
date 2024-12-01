package org.itzixi.service;

import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.utils.PagedGridResult;

public interface IFriendRequestService {

    public void addNewRequest(NewFriendRequestBO friendRequestBO);

    public PagedGridResult queryNewFriendList(String userId,
                                              Integer page,
                                              Integer pageSize);
}
