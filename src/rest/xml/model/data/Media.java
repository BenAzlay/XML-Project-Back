package rest.xml.model.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Media extends IdentifiedResource implements Serializable
{
	private static final long serialVersionUID = 2L;
	
	private int uploaderId;
	private String title;
	private String author;
	private String year;
	private String genre;
	private String type;
	private String city;
	
	public Media(int id,
			int uploaderId,
			String title,
			String author,
			String year, 
			String genre, 
			String type, 
			String city)
	{
		super(id);
		this.uploaderId = uploaderId;
		this.title = title;
		this.author = author;
		this.year = year;
		this.genre = genre;
		this.type = type;
		this.city = city;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getUploaderId() {
		return uploaderId;
	}
	public void setUploaderId(int uploaderId) {
		this.uploaderId = uploaderId;
	}

	@Override
	public String toString() {
		return "MediaResource " + id + title + author + year + genre + type + city + uploaderId;
	}
	
	
}
