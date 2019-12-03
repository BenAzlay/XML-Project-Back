package rest.xml.model.dao;

import java.util.HashMap;
import java.util.Set;

import rest.xml.model.data.IdentifiedResource;
import rest.xml.model.data.User;

public class UserDao extends GenericDao<User> {

	public static UserDao instance = null;
	public static final String USER_SAVE_FILE = "users.ser";

    private UserDao()
    {
    	fileName = USER_SAVE_FILE;
    	content = load();
    	if(content == null)
    	{
    		//Default content
    		content = new HashMap<Integer, User>();
    		User user = new User(IdentifiedResource.generateNewId(getKeys()), "BenAzlay", "TopSecret");
            content.put(user.getId(), user);
            user = new User(IdentifiedResource.generateNewId(getKeys()), "Tomtom", "Backinblack");
            content.put(user.getId(), user);
    	}
    }
    
    /**
     * Singleton pattern method, returns a new instance if it does not exist
     * else returns the existing instance
     */
    public static UserDao getInstance() 
    {
        // To ensure only one instance is created 
        if (instance == null) 
        { 
            instance = new UserDao(); 
        }
        return instance; 
    }
    
    /**
     * Checks if the provided credentials match a stored user
     * @param username
     * @param password
     * @return A valid user if there is a match, an empty user otherwise
     */
    public User authenticate(String username, String password)
    {
    	User currentUser = null;
    	Set<Integer> keys = content.keySet();
    	for(int key : keys)
    	{
    		currentUser = content.get(key);
    		if(currentUser.getUsername().equals(username) && currentUser.getPassword().equals(password))
    		{
    			break;
    		}
    		else
    		{
    			currentUser = null;
    		}
    	}
    	return currentUser;
    }
}