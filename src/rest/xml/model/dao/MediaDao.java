package rest.xml.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import rest.xml.model.data.IdentifiedResource;
import rest.xml.model.data.Media;

public class MediaDao extends GenericDao<Media> {

	public static MediaDao instance = null;
	public static final String MEDIA_SAVE_FILE = "media.ser";
	
    private MediaDao() {
    	fileName = MEDIA_SAVE_FILE;
    	content = load();
    	if(content == null)
    	{
    		content = new HashMap<Integer, Media>();
            Media media = new Media(IdentifiedResource.generateNewId(getKeys()), 1, "Pride and Prejudice", "Jane Auster", "1814", "Romance", "Book", "London");
            content.put(media.getId(), media);
    	}
    }
    
    /**
     * Singleton pattern method, returns a new instance if it does not exist
     * else returns the existing instance
     */
    public static MediaDao getInstance() 
    {
        // To ensure only one instance is created 
        if (instance == null) 
        { 
            instance = new MediaDao(); 
        }
        return instance; 
    }
    
    /**
     * Returns all media belonging to a specific user
     * @param id The user's ID
     * @return A list of media belonging to the user
     */
    public ArrayList<Media> getMediaByUser(int id)
    {
    	ArrayList<Media> matches = new ArrayList<Media>();
    	Collection<Media> medias = content.values();
    	for(Media m : medias)
    	{
    		if(m.getUploaderId() == id)
    		{
    			matches.add(m);
    		}
    	}
    	return matches;
    }
    
    /**
     * Deletes all media belonging to a specific user
     * @param id The user's ID
     */
    public void deleteUserMedia(int id)
    {
    	Media media;
    	Set<Integer> mediaKeys = content.keySet();
    	for(int key : mediaKeys)
        {
        	media = content.get(key);
        	if(media.getUploaderId() == id)
        	{
        		content.remove(key);
        	}
        }
    	save();
    }
    
    /**
     * Searches for media that match the query
     * Query is matched against contents of the media
     * @param query The string to match
     * @return A list of media that match the query
     */
    public ArrayList<Media> searchMedia(String query)
    {
    	ArrayList<Media> matches = new ArrayList<Media>();
    	Collection<Media> medias = content.values();
    	
    	for(Media m : medias)
    	{
    		if(m.getTitle().toLowerCase().contains(query)
    				|| m.getAuthor().toLowerCase().contains(query)
    				|| m.getYear().toLowerCase().contains(query)
    				|| m.getGenre().toLowerCase().contains(query)
    				|| m.getCity().toLowerCase().contains(query))
    		{
    			matches.add(m);
    		}
    	}
    	return matches;
    }
}