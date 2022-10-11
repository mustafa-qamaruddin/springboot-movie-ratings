package com.qubits.demo.repositories;

import com.qubits.demo.models.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends PagingAndSortingRepository<Movie, String> {
  @Override
  Iterable<Movie> findAll();

  Iterable<Movie> findAllByTitleContaining(@Param("title") String title, Pageable pageable);
}
