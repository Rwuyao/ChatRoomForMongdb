package springboot.Facedetect.service;

import java.util.List;

import springboot.Facedetect.entity.Friends;
import springboot.Facedetect.entity.UserInfo;

public interface UserSerice {

	void addUser(UserInfo user);
	
	void delUser(String username);	
    //修改在线状态
    void updatejointime(String username,boolean online); 
    
    int addFriends(String username,String friend);
    
    void delFriends(String username,Friends friend);
    
    boolean checkUsername(String username);
    
    UserInfo findByUsername(String username);
    
    List<Friends> findfriends(String username);
}
