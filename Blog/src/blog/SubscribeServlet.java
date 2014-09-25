package blog;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.dao.SubscriberDAO;

public class SubscribeServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String email = req.getParameter("email");
		String name = req.getParameter("name");
		if (!SubscriberDAO.INSTANCE.addSubscriber(name, email)) {
			// redirect to unsuccessful page
			resp.sendRedirect("/subscribe_unsuccessful.jsp");
		}
		else {
			// redirect to confirmation page
			resp.sendRedirect("/subscribe_successful.jsp");
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// if we arrive here via get, forward to subscribe
		resp.sendRedirect("/subscribe.jsp");
	}
	
}
