package com.qubits.demo.services;

import com.qubits.demo.models.Movie;
import com.qubits.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  @Autowired
  MovieRepository movieRepository;

  public Page<Movie> getAllMovies(Pageable pageable) {
    return movieRepository.findAll(pageable);
  }

  public Iterable<Movie> getAllMoviesByTitleContaining(String title, Pageable pageable) {
    return movieRepository.findAllByTitleContaining(title, pageable);
  }
}
