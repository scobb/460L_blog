package blog.util;

import blog.dao.SubscriberDAO;

public class TestSubscriber {
	public static String getSubscribers() { 
		return "";
	}
	
	public static String addSubscriber(String name, String email) { 
		if (SubscriberDAO.INSTANCE.addSubscriber(name, email)) {
			return "Successfully added " + name + " at email " + email;
		}
		return "Did not successfully add.";
	}

}
