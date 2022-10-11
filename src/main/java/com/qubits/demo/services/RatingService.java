package com.qubits.demo.services;

import com.qubits.demo.exceptions.ResourceNotFoundException;
import com.qubits.demo.models.Rating;
import com.qubits.demo.repositories.MovieRepository;
import com.qubits.demo.repositories.RatingRepository;
import com.qubits.demo.repositories.UserRepository;
import org.hibernate.LockMode;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hibernate.LockMode.PESSIMISTIC_WRITE;

@Service
public class RatingService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  MovieRepository movieRepository;
  @Autowired
  RatingRepository ratingRepository;
  @PersistenceContext
  private EntityManager entityManager;

  public Rating createRating(String userId, String movieId, int value) {
    var userOpt = userRepository.findById(userId);
    if (userOpt.isEmpty()) {
      throw new ResourceNotFoundException("invalid user id" + userId);
    }
    var movieOpt = movieRepository.findById(movieId);
    if (movieOpt.isEmpty()) {
      throw new ResourceNotFoundException("invalid movie id" + movieId);
    }
    var movie = movieOpt.get();
    var rating = ratingRepository.save(new Rating(
        UUID.randomUUID().toString(), userOpt.get(), movie, value
    ));
    entityManager.lock(movie, LockModeType.PESSIMISTIC_WRITE);
    synchronized (movie) {
      var newAvg = updateAvg(movie.getAverageRating(), movie.getRatingsCount(), value);
      movie.setAverageRating(newAvg);
      movie.setRatingsCount(movie.getRatingsCount() + 1);
    }
    entityManager.refresh(movie);
    return rating;
  }

  public double updateAvg(double oldAvg, int newVal, int newCount) {
    return oldAvg + (newVal - oldAvg) / newCount;
  }
}
