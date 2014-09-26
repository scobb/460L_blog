package blog.util;

import java.util.List;

import blog.dao.BlogDAO;
import blog.entity.BlogPost;

import com.google.appengine.api.datastore.Text;

/**
 * @author Steve Cobb scc2448 Sep 20, 2014
 * */
public class TestBlogPostLoad {
	public static boolean addPosts() {
		String author = "Steve Cobb";
		String title = "Test Title 1";
		Text body = new Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
		Boolean post1 = BlogDAO.INSTANCE.saveBlogPost(author, title, body);
		
		author = "Jon Reynolds";
		title = "Test Title 2";
		body = new Text("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		Boolean post2 = BlogDAO.INSTANCE.saveBlogPost(author, title, body);
		
		author = "Jon Reynolds";
		title = "Test Title 3";
		body = new Text("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		Boolean post3 = BlogDAO.INSTANCE.saveBlogPost(author, title, body);
		
		author = "Jon Reynolds";
		title = "Test Title 4";
		body = new Text("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		Boolean post4 = BlogDAO.INSTANCE.saveBlogPost(author, title, body);
		
		author = "Jon Reynolds";
		title = "Test Title 5";
		body = new Text("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		Boolean post5 = BlogDAO.INSTANCE.saveBlogPost(author, title, body);
		
		return post1 && post2 && post3 && post4 && post5;
	}

	public static String markAsNotNew() {
		BlogPost bp = BlogDAO.INSTANCE.getBlogPost("1");
		if (BlogDAO.INSTANCE.markAsNotNew(bp)) {
			return "Marked as not new.";
		}
		return "Marking as not new FAILED";
	}

	public static String getBlogPost() {
		return BlogDAO.INSTANCE.getBlogPost("1").displayHtml();
	}

	public static String getBlogPosts() {
		String retStr = "";
		List<BlogPost> posts = BlogDAO.INSTANCE.getBlogPosts();
		for (BlogPost post : posts) {
			retStr += post.displayHtml();
		}

		return retStr;
	}

	public static String deleteAllPosts() {
		return BlogDAO.INSTANCE.deleteAllPosts();
	}

	public static String getNewPosts() {
		List<BlogPost> newPosts = BlogDAO.INSTANCE.getNewBlogPosts();
		if (newPosts != null) {
			String ret_str = "";
			for (BlogPost post : newPosts) {
				ret_str += post.displayHtml();
			}
			return ret_str;
		}
		return "No new posts.";
	}

}
