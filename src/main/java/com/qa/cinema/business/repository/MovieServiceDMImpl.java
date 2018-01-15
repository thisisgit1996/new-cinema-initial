package com.qa.cinema.business.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.cinema.persistence.Movie;
import com.qa.cinema.util.JSONUtil;

@Transactional(Transactional.TxType.SUPPORTS)
public class MovieServiceDMImpl {
	
	private JSONUtil JU = new JSONUtil();
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	
	public List<Movie> getAllMovies() {
		TypedQuery<Movie> query = em.createQuery("Select m from Movie m "
				+ "order by m.title Desc", Movie.class);
		return query.getResultList();
	}
	
	public Movie findAMovie(Long id) {
		return em.find(Movie.class, id);
		
	}
	@Transactional(Transactional.TxType.REQUIRED)
	public void createMovieFromString(String JSONCreateMovie) {
		Movie movie = JU.getObjectForJSON(JSONCreateMovie, Movie.class);
		em.persist(movie);
		
	}
	@Transactional(Transactional.TxType.REQUIRED)
	public void updateMovie(Long id, String update) {
		Movie newMovie = JU.getObjectForJSON(update, Movie.class);
		Movie currentMovie = em.find(Movie.class, id);
		if(newMovie != null) {
			currentMovie = newMovie;
		}
		em.merge(currentMovie);
	}
	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteMovie(Long id) {
		em.remove(em.find(Movie.class, id));
	}

}
