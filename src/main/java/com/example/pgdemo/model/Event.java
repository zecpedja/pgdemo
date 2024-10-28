package com.example.pgdemo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String title;
  private String description;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}