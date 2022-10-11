package com.qubits.demo.models;

import com.jayway.jsonpath.internal.function.numeric.Average;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
  @Id
  String id;
  @NotNull
  String title;
  @CreatedDate
  private LocalDateTime created;
  @LastModifiedDate
  private LocalDateTime modified;
  @OneToMany(mappedBy = "movie")
  Set<Rating> ratings;
  int ratingsCount;
  double averageRating;
}
