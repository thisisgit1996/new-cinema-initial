package com.qa.cinema.intergration;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.qa.cinema.business.repository.MovieServiceDMImpl;
import com.qa.cinema.persistence.Movie;
import com.qa.cinema.util.JSONUtil;

@Path("/cinema")
public class MovieEndpoint {
	
	@Inject
	private MovieServiceDMImpl service;
	@Inject
	private JSONUtil ju;
	
	@GET
	@Path("{id}")
	public String findAMovie(@PathParam("id") Long id) {
		Movie movie = service.findAMovie(id);
		/*if(movie==null) {
			return new BadRequestException().getResponse();
		}	*/	
		return ju.getJSONForObject(movie);
	}
	
	@GET
	@Path("/json")
	public String getAllMovies() {
		return ju.getJSONForObject(service.getAllMovies());
	}
	
	@POST
	@Path("/json")
	public String addMovie(String newMovieDetails) {
		service.createMovieFromString(newMovieDetails);
		return "Movie added";
		
	}
	
	@PUT
	@Path("/json/{id}")
	public String updateMovie(@PathParam("id") Long id, String newDetails) {
		service.updateMovie(id, newDetails);
		return "Movie updated.";
	}
	
	@DELETE
	@Path("/json/{id}")
	public String deleteMovie(@PathParam("id") Long id) {
		service.deleteMovie(id);
		return "Movie Deleted";
	}
	
	
}
