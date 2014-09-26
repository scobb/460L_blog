package blog.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import blog.entity.BlogPost;
import blog.entity.Subscriber;
import blog.services.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public enum SubscriberDAO {
	INSTANCE;
	public List<Subscriber> getSubscribers() {
		/** returns list of subscribers or null on error **/
		List<Subscriber> Subscribers = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Subscriber.class);
		try {
			Subscribers = (List<Subscriber>) query.execute();
		} catch (Exception e) {
			// handled by null return value

		} finally {
			pm.close();
		}
		return Subscribers;
	}

	public Subscriber getSubscriber(String email) {
		/** returns Subscriber or null if subscriber cannot be found **/
		Key key = KeyFactory.createKey(Subscriber.class.getSimpleName(), email);

		Subscriber found = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			found = pm.getObjectById(Subscriber.class, key);
		} catch (Exception e) {
			// nothing to do - return value should take care of it
		} finally {
			pm.close();
		}
		return found;
	}

	public boolean addSubscriber(String name, String email) {
		/**
		 * returns true on successful add; false on already exists or
		 * unsuccessful add
		 **/
		Subscriber found = getSubscriber(email);
		if (found != null) {
			// e-mail already exists for some subscriber
			return false;
		}
		Key key = KeyFactory.createKey(Subscriber.class.getSimpleName(), email);
		Subscriber newSub = new Subscriber(name, key);
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(newSub);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			pm.close();
		}
	}

	public boolean removeSubscriber(String email) {
		/**
		 * returns true on successful removal; false if couldn't be found or
		 * couldn't be removed
		 **/
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Subscriber.class.getSimpleName(), email);
		Subscriber found = pm.getObjectById(Subscriber.class, key);
		if (found == null) {
			return false;
		}
		try {
			pm.deletePersistent(found);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			pm.close();
		}
	}
}
