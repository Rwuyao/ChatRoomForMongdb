package springboot.Facedetect.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import springboot.Facedetect.entity.Friends;
import springboot.Facedetect.entity.UserInfo;

@Service 
public class UserServiceImpl implements UserSerice {

	 @Autowired
	 private MongoTemplate mongoTemplate;
	 
	 @Autowired
	 private UserRepository userRepository;
	 	 
		public List<Friends> findfriends(String username) {			
			return (userRepository.findByUsername(username)).getFriend();
		}
	 
	 public UserInfo findByUsername(String username){
		 UserInfo user =userRepository.findByUsername(username);
		 return user;
	 }
	 
	 public boolean checkUsername(String username){
		 UserInfo user =userRepository.findByUsername(username);
		 if(user == null){
			 return false;
		 }
		 return true;
	 }
	 
	 public void addUser(UserInfo user){
		
		 mongoTemplate.save(user);
	 }
		
	public void delUser(String username){
		Query query=new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query,UserInfo.class);	
		}
		
	    //修改在线状态
	    public void updatejointime(String username,boolean online){
	    	Query query=new Query(Criteria.where("username").is(username));
	        Update update= new Update().set("online",true).set("jointime", LocalDateTime.now());
	        //更新查询返回结果集的第一条
	        mongoTemplate.updateFirst(query,update,UserInfo.class);
	    }
	    
	    public int addFriends(String username,String friend){ 	
	    	UserInfo add=userRepository.findByUsername(friend);
	    	if(add != null){  		 
	    		UserInfo user=userRepository.findByUsername(username);
	    		List<Friends> friends =user.getFriend();
	    		if(friends == null)
	    			friends = new ArrayList<Friends>();
	    		Friends f = new Friends(friend);	    		
	    		if(!friends.contains(f)){
	    			Friends u = new Friends(username);
	    			u.setNickname(user.getNickname());
	    			u.setOnline(user.isOnline());
	    			u.setProfilephoto(user.getProfilephoto());
	    			List<Friends> users = user.getFriend();
	    			if(users == null)
	    				users = new ArrayList<Friends>();
	    			users.add(u);
	    			f.setNickname(add.getNickname());
	    			f.setOnline(add.isOnline());
	    			f.setProfilephoto(add.getProfilephoto());
	    			friends.add(f);	    	
	    			Query query=new Query(Criteria.where("username").is(username));
	    			Update update= new Update().set("friend",friends);
	    			//更新查询返回结果集的第一条
	    			mongoTemplate.updateFirst(query,update,UserInfo.class);
	    			
	    			Query query1=new Query(Criteria.where("username").is(friend));
	    			Update update1= new Update().set("friend",users);
	    			//更新查询返回结果集的第一条
	    			mongoTemplate.updateFirst(query1,update1,UserInfo.class);
	    			return 3;
	    		}
	    		return 2;
	    	}
	    	return 1 ;
	    }
	    
	    public void delFriends(String username,Friends f){
	    	UserInfo user=userRepository.findByUsername(username);
	    	List<Friends> friends =user.getFriend();
	    	if(friends.contains(f)){
	    		friends.remove(f);	    	
	    		Query query=new Query(Criteria.where("username").is(username));
	    		Update update= new Update().set("friend",friends);
	    		//更新查询返回结果集的第一条
	    		mongoTemplate.updateFirst(query,update,UserInfo.class);
	    	}
	    }

		
}
