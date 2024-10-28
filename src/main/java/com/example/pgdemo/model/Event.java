package com.example.pgdemo.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
  private Long id;
  private String title;
  private String description;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
