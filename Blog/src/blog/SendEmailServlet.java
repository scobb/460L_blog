package blog;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.dao.BlogDAO;
import blog.dao.SubscriberDAO;
import blog.entity.BlogPost;
import blog.entity.Subscriber;

public class SendEmailServlet extends HttpServlet
{
	public static final Logger _log = Logger.getLogger(SendEmailServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		List<Subscriber> subscribers = SubscriberDAO.INSTANCE.getSubscribers();
		List<BlogPost> newPosts = BlogDAO.INSTANCE.getNewBlogPosts();

		String emailBody = "Hello subscriber,\n";

		if (newPosts.size() == 0)
		{
			// no new posts. sad face.
			emailBody += "There are no new posts from our travel blog. You should contribute!";
		} else
		{
			emailBody += "Here are the new blog posts from our travel blog:\n";

			for (BlogPost post : newPosts)
			{
				// TODO : do we actually want to send html here? or some plaintext
				// version
				emailBody += post.displayHtml();

				// persist that this blog post is no longer new
				BlogDAO.INSTANCE.markAsNotNew(post);
			}
		}
		
		// add unsubscribe message. TODO: is this the right address?
		emailBody += "\nTo unsubscribe, go to http://cobbreynoldsblog.appspot.com/unsubscribe";

		for (Subscriber subscriber : subscribers)
		{
			try
			{
				Address fromAddress = new InternetAddress(
						"admin@cobbreynoldsblog.appspotmail.com");
				Address toAddress = new InternetAddress(subscriber.getEmail()
						.getName());
				String subject = "New travel blog posts!";

				// Send out Email
				MimeMessage outMessage = new MimeMessage(session);
				outMessage.setFrom(fromAddress);
				outMessage.addRecipient(MimeMessage.RecipientType.TO, toAddress);
				outMessage.setSubject(subject);
				outMessage.setText(emailBody);
				Transport.send(outMessage);
			} catch (MessagingException e)
			{
				_log.info("ERROR: Could not send out Email Results response : "
						+ e.getMessage());
			}
		}

	}
}
