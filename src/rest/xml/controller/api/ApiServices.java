package rest.xml.controller.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.xml.model.dao.CommentDao;
import rest.xml.model.dao.MediaDao;
import rest.xml.model.dao.UserDao;
import rest.xml.model.data.Comment;
import rest.xml.model.data.IdentifiedResource;
import rest.xml.model.data.Media;
import rest.xml.model.data.User;

/**
 * API class that handles REST calls
 */
@Path("/")
public class ApiServices {   
	// Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    
    UserDao contentUser = UserDao.getInstance();
    MediaDao contentMedia = MediaDao.getInstance();
    CommentDao contentComment = CommentDao.getInstance();

    /**********************USERS*****************/
    // Return the list of users for applications
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> getUsers() {
        ArrayList<User> users = contentUser.getAll();
        return users;
    }
    
    // Return user by id
    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        User user = contentUser.get(id);
        return user;
    }
    
    //Attempt to log in
    @GET
    @Path("/user/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public User login(@PathParam("username") String username,
            @PathParam("password") String password){
    	return contentUser.authenticate(username, password);
    }
    
    //Register a new user
    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newUser(@FormParam("username") String username,
            @FormParam("password") String password){
        User user = new User(IdentifiedResource.generateNewId(contentUser.getKeys()), username, password);
        contentUser.add(user);
    }
    
    //Update a user
    @PUT
    @Path("/user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateUser(@FormParam("id") int id,
            @FormParam("username") String username,
            @FormParam("password") String password){
    	if(contentUser.get(id) != null){
            contentUser.get(id).setUsername(username);
            contentUser.get(id).setPassword(password);
            contentUser.save();
    	}
    	else{
    		System.out.println("User " + id + " doesn't exist");
    	}

    }
    
    //Remove a user
    @DELETE
    @Path("/user/{id}")
    public void deleteUser(@PathParam("id") int id){
    	//If a user gets deleted, its comments and media will also be
        contentUser.delete(id);
        contentComment.deleteUserComments(id);
        contentMedia.deleteUserMedia(id);
    }
    
    /**********************MEDIA*****************/
    //Return all media
    @GET
    @Path("/media")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Media> getMedias() {
        ArrayList<Media> medias = contentMedia.getAll();
        return medias;
    }
    
    // Return media by id
    @GET
    @Path("/media/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Media getMedia(@PathParam("id") int id) {
        Media media = contentMedia.get(id);
        return media;
    }
    
    // Return media by user
    @GET
    @Path("user/{id}/media")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Media> getMediaByUser(@PathParam("id") int id) {
        ArrayList<Media> media = contentMedia.getMediaByUser(id);
        return media;
    }
    
    //Search media
    @GET
    @Path("/search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Media> search(@PathParam("keyword") String keyword){
    	return contentMedia.searchMedia(keyword.toLowerCase());
    }
    
    //Create new media
    @POST
    @Path("/media")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newMedia(@FormParam("uploaderId") int uploaderId,
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("year") String year,
            @FormParam("genre") String genre,
            @FormParam("type") String type,
            @FormParam("city") String city){
        Media media = new Media(IdentifiedResource.generateNewId(contentMedia.getKeys()), uploaderId, title, author, year, genre, type, city);
        contentMedia.add(media);
    }
    
    //Update media
    @PUT
    @Path("/media")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateMedia(@FormParam("id") int id,
    		@FormParam("uploaderId") int uploaderId,
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("year") String year,
            @FormParam("genre") String genre,
            @FormParam("type") String type,
            @FormParam("city") String city){
    	if(contentMedia.get(id) != null){
    		contentMedia.get(id).setUploaderId(uploaderId);
    		contentMedia.get(id).setTitle(title);
    		contentMedia.get(id).setAuthor(author);
    		contentMedia.get(id).setYear(year);
    		contentMedia.get(id).setGenre(genre);
    		contentMedia.get(id).setType(type);
    		contentMedia.get(id).setCity(city);
    		contentMedia.save();
    	} else {
    		System.out.println("Media" + id + " doesn't exist");
    	}
    }
    
    //Remove media
    @DELETE
    @Path("/media/{id}")
    public void deleteMedia(@PathParam("id") int id){
    	//If a media gets deleted, its comments will also be
    	contentMedia.delete(id);
    	contentComment.deleteMediaComments(id);
    }
    
    /**********************COMMENTS*****************/
    
    //Get all comments
    @GET
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Comment> getComments() {
        ArrayList<Comment> comments = contentComment.getAll();
        return comments;
    }
    
    // Return comment by id
    @GET
    @Path("/comment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment getComment(@PathParam("id") int id) {
        Comment comment = contentComment.get(id);
        return comment;
    }
    
    // Return comments by media
    @GET
    @Path("/media/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Comment> getCommentsByMedia(@PathParam("id") int id) {
        ArrayList<Comment> comments = contentComment.getCommentsByMedia(id);
        return comments;
    }
    
    //Return comments by user
    @GET
    @Path("/user/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Comment> getCommentsByUser(@PathParam("id") int id) {
        ArrayList<Comment> comments = contentComment.getCommentsByUser(id);
        return comments;
    }
    
    //Create new comment
    @POST
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newComment(@FormParam("mediaId") int mediaId,
    		@FormParam("commenterId") int uploaderId,
            @FormParam("content") String content){
        Comment comment = new Comment(IdentifiedResource.generateNewId(contentComment.getKeys()), mediaId, uploaderId, content);
        contentComment.add(comment);
    }
    
    //Update comment
    @PUT
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateComment(@FormParam("id") int id,
    		@FormParam("mediaId") int mediaId,
    		@FormParam("commenterId") int commenterId,
            @FormParam("content") String content){
        if(contentComment != null) {
        	contentComment.get(id).setMediaId(mediaId);
        	contentComment.get(id).setCommenterId(commenterId);
        	contentComment.get(id).setContent(content);
        	contentComment.save();
        }
        else {
        	System.out.println("Comment " + id + " doesn't exist");
        }
    }
    
    //Remove comment
    @DELETE
    @Path("/comment/{id}")
    public void deleteComment(@PathParam("id") int id){
        contentComment.delete(id);
    }
}
