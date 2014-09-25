package blog.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Subscriber implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY) 
	private Key email;
	private String name;
	
	public Subscriber(String name, Key email)
	{
		this.setName(name);
		this.setEmail(email);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Key getEmail() {
		return email;
	}

	public void setEmail(Key email) {
		this.email = email;
	}

}
