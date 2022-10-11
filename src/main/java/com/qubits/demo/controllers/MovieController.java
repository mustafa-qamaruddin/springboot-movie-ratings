package com.qubits.demo.controllers;

import com.qubits.demo.models.Movie;
import com.qubits.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping(
      path = "/all",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Page<Movie>> getMovies(
      @PageableDefault(page = 0, size = 30)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "modified", direction = Sort.Direction.DESC)
      }) Pageable pageable) {
    Page<Movie> allMovies = movieService.getAllMovies(pageable);
    return ResponseEntity.ok(allMovies);
  }

  @GetMapping(
      path = "/search",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Validated
  public ResponseEntity<Iterable<Movie>> searchMovies(
      @PageableDefault(page = 0, size = 30)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "modified", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @Valid @RequestParam(required = false) String title
  ) {
    Iterable<Movie> allMovies = movieService.getAllMoviesByTitleContaining(title, pageable);
    return ResponseEntity.ok(allMovies);
  }
}
