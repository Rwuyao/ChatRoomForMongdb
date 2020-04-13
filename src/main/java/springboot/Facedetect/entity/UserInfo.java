package springboot.Facedetect.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class UserInfo {
	@Id
	private String id;
	private String username;
	private String password;
	private String nickname;
	private String profilephoto;
	private LocalDateTime jointime;
	private boolean online; 
	private List<Friends> friend;
	
	public UserInfo() {
		super();
	}
		
	public UserInfo(String username, String password, String nickname, String profilephoto) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.profilephoto = profilephoto;
		this.online = false;
		this.jointime = LocalDateTime.now();
	}



	public List<Friends> getFriend() {
		return friend;
	}


	public void setFriend(List<Friends> friend) {
		this.friend = friend;
	}


	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfilephoto() {
		return profilephoto;
	}
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}
	public LocalDateTime getJointime() {
		return jointime;
	}
	public void setJointime(LocalDateTime jointime) {
		this.jointime = jointime;
	}
	@Override
	public String toString(){
		
		String str="[id="+id+";username="+username+";nickname="+nickname+
				";jointime="+jointime+";online="+online+";Friends=";
		if(friend == null)
			friend =new ArrayList<Friends>();
		for(Friends f : friend){
			str+=f.getUsername()+",";
		}
		return str+"]";
	}
}
