package org.itzixi.service;

import org.itzixi.pojo.Friendship;
import org.itzixi.pojo.vo.ContactsVO;

import java.util.List;

public interface IFriendshipService {

    public Friendship getFriendship(String myId,String friendId);

    public List<ContactsVO> getFriendship(String myId);
}
