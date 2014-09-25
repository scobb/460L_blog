package blog;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.dao.SubscriberDAO;

public class UnsubscribeServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String email = req.getParameter("email");
		if (!SubscriberDAO.INSTANCE.removeSubscriber(email)) {
			// TODO: check for this cookie on the remove_email.html page to display an "Invalid email" message
			Cookie cookie = new Cookie("badEmail", "true");
	      cookie.setMaxAge(3);
			resp.addCookie(cookie);
			// TODO: redirect to page that directed us here, say email wasn't found
			resp.sendRedirect("/remove_email.jsp");
		}
		
		// TODO: generate thanks.html, confirm that address is correct
		resp.sendRedirect("/thanks.html");
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//TODO: confirm this address
		resp.sendRedirect("/remove_email.jsp");
	}

}
