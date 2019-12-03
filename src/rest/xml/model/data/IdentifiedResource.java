package rest.xml.model.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Generic class to handle the ID field
 */
public class IdentifiedResource implements Serializable {

	private static final long serialVersionUID = 4L;
	
	protected int id;
	
	public IdentifiedResource(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Generates a new unique ID based on existing ones.
	 * The new ID is greater than existing ones
	 * @param keys A key set that corresponds to existing IDs
	 * @return A new, unique ID
	 */
	public static <T> int generateNewId(Set<Integer> keys)
	{
		if(keys.size() == 0){
			return 1;
		}
		
		//Ensure the new ID is unique by making it the largest
		return Collections.max(keys) + 1; 
	}
}
