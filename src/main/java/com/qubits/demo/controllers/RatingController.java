package com.qubits.demo.controllers;

import com.qubits.demo.models.Rating;
import com.qubits.demo.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/rating")
public class RatingController {
  @Autowired
  RatingService ratingService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Validated
  public ResponseEntity<Rating> interconnections(
      @Valid @RequestParam(required = true) String userId,
      @Valid @RequestParam(required = true) String movieId,
      @Valid @RequestParam(required = true) int value
  ) {
    return ResponseEntity.ok(ratingService.createRating(userId, movieId, value));
  }
}
