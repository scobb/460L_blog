package blog;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.dao.SubscriberDAO;

public class UnsubscribeServlet extends HttpServlet
{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{

		String email = req.getParameter("email");
		if (!email.equals(""))
		{

			if (!SubscriberDAO.INSTANCE.removeSubscriber(email))
			{
				resp.sendRedirect("/unsubscribe_unsuccessful.jsp");
			} else
			{
				resp.sendRedirect("/unsubscribe_successful.jsp");
			}
		} else
		{
			resp.sendRedirect("/unsubscribe_unsuccessful.jsp");
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		// TODO: confirm this address
		resp.sendRedirect("/unsubscribe.jsp");
	}

}
