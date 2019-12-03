package rest.xml.model.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User extends IdentifiedResource implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public User(int id, String username, String password)
	{
		super(id);
		this.username = username;
		this.password = password;
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
}