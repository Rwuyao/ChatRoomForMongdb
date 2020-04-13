package springboot.Facedetect.message;

public class BaseMessage {

	// 消息ID
	// @Id
	// private String id;

	// 消息类型
	private String type;

	// 消息内容
	private String content;

	// 发送者
	private String sender;

	// 接受者 类型
	private String toType;

	// 接受者
	private String receiver;


	public void setSender(String name) {
		// TODO Auto-generated method stub
		sender = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		// TODO Auto-generated method stub
		return sender;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}


	public String getReceiver() {
		// TODO Auto-generated method stub
		return receiver;
	}

	@Override
    public String toString() {
        return 
                "type='" + type + '\'' +
                ", content='" + content +'\'' +
                ", sender='" +  sender + '\'' +
                ", toType='" + toType + '\''+
                ", receiver='" + receiver +'\'';
    }
}
