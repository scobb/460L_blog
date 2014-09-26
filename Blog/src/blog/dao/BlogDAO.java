package blog.dao;

import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import blog.entity.BlogPost;
import blog.services.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public enum BlogDAO {
	INSTANCE;

	public List<BlogPost> getBlogPosts()
	{
		List<BlogPost> blogPosts;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BlogPost.class);
		query.setOrdering("timestamp desc");
		try
		{
			blogPosts = (List<BlogPost>) query.execute();
		} finally
		{
			pm.close();
		}
		
//		Collections.sort(blogPosts, new Comparator<BlogPost>{
//			@Override
//			int compare (BlogPost bp1, BlogPost bp2) {
//				return (bp1.getTimestamp()).compareTo((bp2.getTimestamp()));
//			}
//		});
		
		return blogPosts;

	}

	public List<BlogPost> getNewBlogPosts() {
		/** returns list of new posts or null if there are no new posts **/
		List<BlogPost> posts = getBlogPosts();
		if (posts == null) {
			return null;
		}
		Iterator<BlogPost> iter = posts.iterator();
		while (iter.hasNext()) {
			if (!iter.next().isNewPost()) {
				// remove non-new posts
				iter.remove();
			}
		}
		return posts;
	}

	public boolean markAsNotNew(BlogPost bp) {
		/** returns true if blog post is successfully marked as not new **/
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			BlogPost bpData = pm.getObjectById(BlogPost.class, bp.getTitle());
			bpData.setNewPost(false);
		} catch (Exception e) {
			return false;
		} finally {
			pm.close();
		}

		return true;
	}

	public BlogPost getBlogPost(String title) {

		Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), title);

		PersistenceManager pm1 = PMF.get().getPersistenceManager();
		BlogPost found = null;
		try {
			found = pm1.getObjectById(BlogPost.class, key);
		} catch (Exception e) {
			// not found
			return null;
		} finally {
			pm1.close();
		}
		return found;
	}

	public boolean saveBlogPost(String author, String title, Text body) {
		/**
		 * returns true on successful save, false if post with same title
		 * already exists
		 **/
		// title is key
		Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), title);

		// check if this title already exists

		// create new object
		BlogPost newPost = new BlogPost(author, key, body);

		BlogPost exists = null;
		exists = getBlogPost(title);

		if (exists != null) {
			return false;
		}

		// now we can save
		synchronized (this) {

			PersistenceManager pm = PMF.get().getPersistenceManager();

			BlogPost result = null;
			try {
				pm.makePersistent(newPost);
				// result = pm.getObjectById(BlogPost.class, key);
			} catch (Exception e) {
				// something went wrong
				return false;
				// StringWriter sw = new StringWriter();
				// PrintWriter pw = new PrintWriter(sw);
				// e.printStackTrace(pw);
				// return "Exception in add: " + sw.toString(); // stack trace
				// as a
				// // string
			} finally {
				pm.close();

			}

		}

		// success
		return true;

	}

	public String deleteAllPosts() {
		List<BlogPost> posts;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BlogPost.class);
		try {
			posts = (List<BlogPost>) query.execute();
			pm.deletePersistentAll(posts);
		} catch (Exception e) {
			return "Exception: " + e.getMessage();
		} finally {
			pm.close();
		}

		return "All posts deleted.n";
	}

}
