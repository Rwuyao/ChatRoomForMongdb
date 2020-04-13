package springboot.Facedetect.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

public class fileprefix {
	
	@Value("${random.value}")
	int rodom;	
	LocalDateTime Timenow;	
	String name;
	public fileprefix(){
	  Timenow = LocalDateTime.now();
	  name= Timenow.format(DateTimeFormatter.ofPattern("uuuuMMdHHmmss"))
			  +String.valueOf(rodom);	  
	}
	public String getName() {
		return name;
	}	
}
