package com.qubits.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
  @Id
  String id;
  @ManyToOne
  Customer customer;
  @ManyToOne
  Movie movie;
  @NotNull
  @Min(1)
  @Max(5)
  @Column(name = "rating_value")
  int value;
  @CreatedDate
  private LocalDateTime created;
  @LastModifiedDate
  private LocalDateTime modified;

  public Rating(String id, Customer customer, Movie movie, int value) {
    this.id = id;
    this.customer = customer;
    this.movie = movie;
    this.value = value;
  }
}
