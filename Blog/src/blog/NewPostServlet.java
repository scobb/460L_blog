package blog;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.dao.BlogDAO;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class NewPostServlet extends HttpServlet {
	private static final Logger _logger = Logger.getLogger(NewPostServlet.class.getName());
	Boolean failure;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		failure = false;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String author = null;
		if (user != null) {
			// get poster's name
			author = user.getNickname();
		} else {
			// User is anonymous and cannot post; should not be on this page
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			return;
		}
		Text body = new Text(req.getParameter("body"));
		String title = "";
		title = req.getParameter("title");
		_logger.log(Level.INFO, "body " + body);
		if (body.equals("") || title.equals("")) {
			failure = true;
		}
		try {
			if (!body.equals("") && !title.equals("")) {
				// create post object
				if (!BlogDAO.INSTANCE.saveBlogPost(author, title, body)) {
					_logger.log(Level.INFO, "save " + BlogDAO.INSTANCE.saveBlogPost(author, title, body));
					failure = true;
				};
			}
			else {
				failure = true;
			}
		} catch (Exception e) {
			throw new IOException(e.getMessage());

		}
		if (failure) {
			resp.sendRedirect("/post_unsuccessful.jsp");
		}
		else {
			resp.sendRedirect("/post_success.jsp");
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// redirect to the jsp
		resp.sendRedirect("new_post.jsp");
	}
}
