package springboot.Facedetect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import springboot.Facedetect.entity.Friends;
import springboot.Facedetect.entity.UserInfo;
import springboot.Facedetect.message.BaseMessage;
import springboot.Facedetect.message.ChatMessage;
import springboot.Facedetect.service.UserSerice;

@Controller
public class homeController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private UserSerice userservice;
	
	@RequestMapping("/addfriend")
	 public void addfriend(HttpServletRequest request, HttpServletResponse response) 		  
			  throws ServletException, IOException {  
			  String friend = request.getParameter("friend");
			  PrintWriter out = response.getWriter();   
			  if (friend.trim().equals("")) {
			            out.print(2);// 2是不能为空
			        } else {
			        	UserInfo user = (UserInfo)SecurityUtils.getSubject().getPrincipal();
			            int flag = userservice.addFriends(user.getUsername(), friend);			           
			                out.print(flag);
			            }
		  	}
	
	@RequestMapping(value = "/chat")
	public String chat(Model model) {
		String username = ((UserInfo)SecurityUtils.getSubject().getPrincipal()).getUsername();
		UserInfo user = userservice.findByUsername(username);
		model.addAttribute("user", user);
		List<Friends> friends = user.getFriend();
		model.addAttribute("friends", friends);
		return "chatbox";
	}
	
	@MessageMapping("/all")
	public void all(String message) {
		BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);		
		ChatMessage chatMessage = createMessage(baseMessage.getSender(),baseMessage.getContent());
		template.convertAndSend("/topic/notice", JSON.toJSONString(chatMessage));
	}

	@MessageMapping("/chat")
	public void chat(String message) {
		BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
		ChatMessage chatMessage = createMessage(baseMessage.getSender(), baseMessage.getContent());
		System.out.println(baseMessage.getReceiver());
		template.convertAndSendToUser(baseMessage.getReceiver(), "/topic/chat", JSON.toJSONString(chatMessage));
	}


	private ChatMessage createMessage(String username, String message) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(username);
		UserInfo user = userservice.findByUsername(username);
		chatMessage.setProfilephoto(user.getProfilephoto());
		chatMessage.setNickname(user.getNickname());
		chatMessage.setContent(message);
		chatMessage.setSendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm")));
		return chatMessage;
	}
}
