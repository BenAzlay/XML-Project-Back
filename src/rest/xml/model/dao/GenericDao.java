package rest.xml.model.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rest.xml.model.data.IdentifiedResource;

public abstract class GenericDao<T>
{
	String fileName;
	protected Map<Integer, T> content;
	
	public void add(T object)
	{
		content.put(((IdentifiedResource)object).getId(), object);
		save();
	}
	
	public void delete(int id)
	{
		content.remove(id);
		save();
	}
	
	public void update(int id, T object)
	{
		content.put(id, object);
		save();
	}
	
	public T get(int id)
	{
		return content.get(id);
	}
	
	public ArrayList<T> getAll()
	{
		ArrayList<T> objects = new ArrayList<T>();
		objects.addAll(content.values());
		return objects;
	}
	
	public Set<Integer> getKeys()
	{
		return content.keySet();
	}
	
	/**
	 * Serializes the DAO's contents into a file
	 */
	public void save()
	{
		try {
			//Clear the save file to write over it
			new PrintWriter(fileName).close();
			FileOutputStream file = new FileOutputStream(fileName); 
            ObjectOutputStream out = new ObjectOutputStream(file);
            
            Set<Integer> keys = content.keySet();
            
            //Serialize all the contents of the map
            for(Integer key : keys)
            {
            	out.writeObject(content.get(key));
            }
            
            //Null object at the end used as a terminator
            out.writeObject(null);
            
            out.close(); 
            file.close(); 
              
            System.out.println(this.getClass().getSimpleName() + ": saving completed"); 
		} catch (IOException e) {
			System.out.println(this.getClass().getSimpleName() + ": saving failed: " + e.getMessage());
		}
	}
	
	/**
	 * Deserializes from a file into the DAO's content
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, T> load()
	{
		{
			Map<Integer, T> loadedContent = new HashMap<Integer, T>();
			T singleObject = null;
			try
	        {     
	            FileInputStream file = new FileInputStream(fileName); 
	            ObjectInputStream in = new ObjectInputStream(file); 
	              
	            //Deserialize the objects stored in the file
	            while(true)
	            {
	            	//Prompts an unchecked cast warning that was suppressed
	            	//No issue at runtime since the type is defined when the method is called
	            	singleObject = (T)in.readObject();
	            	if(singleObject == null) break;
	            	else
	            	{
	            		loadedContent.put(((IdentifiedResource)singleObject).getId(), singleObject);
	            	}
	            }
	              
	            in.close(); 
	            file.close(); 
	              
	            System.out.println(this.getClass().getSimpleName() + ": data loaded successfully"); 
	        } catch (IOException | ClassNotFoundException e) {
	        	System.out.println(this.getClass().getSimpleName() + ": loading failed: " + e.getMessage());
	        	return null;
			}
			return loadedContent;
		}
	}
}