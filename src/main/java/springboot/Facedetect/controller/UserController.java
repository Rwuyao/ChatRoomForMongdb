package springboot.Facedetect.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springboot.Facedetect.entity.UserInfo;
import springboot.Facedetect.service.UserSerice;
import springboot.Facedetect.service.fileprefix;

@Controller
public class UserController {

	@Autowired
	UserSerice userservice;
	
	@Resource
	private HashedCredentialsMatcher hashedCredentialsMatcher;
		
	@RequestMapping("/login")
		public String login(){
			return "login";
		}
			
		@RequestMapping("/logout")
		public String logout(){
			return "logout";
		}
		
		@RequestMapping("/new")
		public String Regsiste(Model model){
			UserInfo user = new UserInfo();
			  model.addAttribute("UserInfo",user);
			return "Register";
		}
		
		@RequestMapping("/regsiste")
		public String RegsisteSusscees(UserInfo user,@RequestParam("file") MultipartFile file){			
			 if (!file.isEmpty()) {      	                       	
		        	String uploadDir = "./webapps/ChatRoom/WEB-INF/classes/static/temp/" ;//./src/main/resources/static/temp/
		        	String filename = file.getOriginalFilename();
		        	String Suffix = filename.substring(filename.indexOf('.')+1);
		        	Pattern pattern = Pattern.compile("jpg|png|bmp|gif");
		        	boolean matchResult =  pattern.matcher(Suffix).matches();
		        	if(matchResult){
		        		try { 
		        			fileprefix f = new fileprefix();
		        			File Dir = new File(uploadDir) ;
		        			if(!Dir.exists()){
		        				Dir.mkdirs();
		        			}             	
		        			BufferedOutputStream out = new BufferedOutputStream(      
		                    new FileOutputStream(uploadDir+f.getName()+"."+Suffix));         
		        			out.write(file.getBytes());      
		        			out.flush();      
		        			out.close();
		        			user.setProfilephoto("temp/"+f.getName()+"."+Suffix);	        			
		        			String passwd=(new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes("salt"), 2)).toHex();
		        			user.setPassword(passwd);
		        			userservice.addUser(user);;
		        			return "redirect:/login";
		        		} catch (Exception e) {                      
		                    e.printStackTrace();      
		                    return "redirect:/404";      
		                } 
		        		
		        	}
		            return "redirect:/404";                                        
		    } else {      
		        return "redirect:/404";      
		    }			
		}
		
		@RequestMapping("/checkusername")
		 public void checkUname(HttpServletRequest request, HttpServletResponse response) 		  
				  throws ServletException, IOException {  
				  String name = request.getParameter("username");
				  PrintWriter out = response.getWriter();
				  System.out.println(name);    
				  if (name.trim().equals("")) {
				            out.print(2);// 2是不能为空
				        } else {
				            boolean flag = userservice.checkUsername(name);
				            if (flag) {
				                out.print(1);// 用户名已存在
				            } else {
				                out.print(3);// 用户名可以用
				            }}
			  	}
		
		
}
