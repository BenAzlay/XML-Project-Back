package rest.xml.model.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment extends IdentifiedResource implements Serializable
{
	private static final long serialVersionUID = 3L;
	private int mediaId;
	private int commenterId;
	private String content;
	
	public Comment(int id, int mediaId, int commenterId, String content)
	{
		super(id);
		this.mediaId = mediaId;
		this.commenterId = commenterId;
		this.content = content;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public int getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(int commenterId) {
		this.commenterId = commenterId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}
