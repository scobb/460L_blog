package blog.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class BlogPost implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key title;
	private String author;
	private Text body;
	private Date timestamp;
	private boolean newPost;
	
	/**Constructor **/
	public BlogPost(String author, Key title, Text body){
		this.setAuthor(author);
		this.title = title;
		this.body = body;
		this.timestamp = new Date();
		this.setNewPost(true);
	}
	/** display method **/
	public String displayHtml() {
		//perhaps form the HTML string of each blog post here?
		// other option is to imbed it in the jsp and access the author, title by getters, setters
		String ret_str = "<h1>" + this.title.getName() + "</h1>";
		if (this.isNewPost()) {
			ret_str += "<br>NEW<br>";
		}
		ret_str += "<hr>";
		ret_str += "<h2>Posted by " + this.author + " on " + this.timestamp + "</h2>";
		ret_str += this.body;
		
		return ret_str;
	}
	
	/** getters and setters **/
	public Key getTitle() {
		return title;
	}

	public void setTitle(Key title) {
		this.title = title;
	}

	public Text getBody() {
		return body;
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isNewPost() {
		return newPost;
	}
	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}

}
