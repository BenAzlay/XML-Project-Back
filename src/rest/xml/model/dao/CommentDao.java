package rest.xml.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import rest.xml.model.data.Comment;
import rest.xml.model.data.IdentifiedResource;

public class CommentDao extends GenericDao<Comment> {
	
	public static CommentDao instance = null;
	public static final String COMMENT_SAVE_FILE = "comments.ser";

    private CommentDao() {

    	fileName = COMMENT_SAVE_FILE;
    	content = load();
    	if(content == null)
    	{
    		content = new HashMap<Integer, Comment>();
    		Comment comment = new Comment(IdentifiedResource.generateNewId(getKeys()), 1, 2, "Best novel ever!");
            content.put(comment.getId(), comment);
            comment = new Comment(IdentifiedResource.generateNewId(getKeys()), 2, 1, "Best song ever!");
            content.put(comment.getId(), comment);
    	}
    }
    
    /**
     * Singleton pattern method, returns a new instance if it does not exist
     * else returns the existing instance
     */
    public static CommentDao getInstance() 
    {
        // To ensure only one instance is created 
        if (instance == null) 
        { 
            instance = new CommentDao(); 
        }
        return instance; 
    }
    
    /**
     * Returns all comments belonging to a specific user
     * @param id The user's ID
     * @return A list of comments belonging to the user
     */
    public ArrayList<Comment> getCommentsByUser(int id)
    {
    	ArrayList<Comment> matches = new ArrayList<Comment>();
    	Collection<Comment> comments = content.values();
    	for(Comment c : comments)
    	{
    		if(c.getCommenterId() == id)
    		{
    			matches.add(c);
    		}
    	}
    	return matches;
    }
    
    /**
     * Returns all comments belonging to a specific media
     * @param id The media's ID
     * @return A list of comments belonging to the media
     */
    public ArrayList<Comment> getCommentsByMedia(int id)
    {
    	ArrayList<Comment> matches = new ArrayList<Comment>();
    	Collection<Comment> comments = content.values();
    	for(Comment c : comments)
    	{
    		if(c.getMediaId() == id)
    		{
    			matches.add(c);
    		}
    	}
    	return matches;
    }
    
    /**
     * Deletes all comments belonging to a specific user
     * @param id The user's ID
     */
    public void deleteUserComments(int id)
    {
    	Comment comment;
    	Set<Integer> commentKeys = content.keySet();
    	for(int key : commentKeys)
        {
        	comment = content.get(key);
        	if(comment.getCommenterId() == id)
        	{
        		content.remove(key);
        	}
        }
    	save();
    }
    
    /**
     * Deletes all comments belonging to a specific media
     * @param id The media's ID
     */
    public void deleteMediaComments(int id)
    {
    	Comment comment;
    	Set<Integer> commentKeys = content.keySet();
    	for(int key : commentKeys)
        {
        	comment = content.get(key);
        	if(comment.getMediaId() == id)
        	{
        		content.remove(key);
        	}
        }
    	save();
    }
}