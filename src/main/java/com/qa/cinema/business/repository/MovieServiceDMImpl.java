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
		TypedQuery<Movie> query = em.createQuery("Select * from Movie "
				+ "order by Movie.title Desc", Movie.class);
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
	public void updateMovie(Movie movie) {
		em.merge(movie);
	}
	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteMovieFromString(String JSONDeleteMovie) {
		Movie movie = JU.getObjectForJSON(JSONDeleteMovie, Movie.class);
		em.remove(movie);
	}

}
