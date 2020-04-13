package springboot.Facedetect.entity;

import java.time.LocalDateTime;

public class Friends {
	private String username;
	private String nickname;
	private boolean online; 
	private String profilephoto;
	private LocalDateTime jointime;
	public Friends() {
		super();
	}
	
	public Friends(String username){
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
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
	public boolean equals(Object o){
		 boolean flag = false;
		    if (o instanceof Friends) {
		    	Friends f = (Friends) o;
		    	flag = this.getUsername().equals(f.getUsername());
		    }
		    return flag;
	}
}
