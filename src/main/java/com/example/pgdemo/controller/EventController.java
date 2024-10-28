package com.example.pgdemo.controller;

import com.example.pgdemo.model.Event;
import com.example.pgdemo.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping
  public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    return ResponseEntity.ok(eventService.createEvent(event));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
    return ResponseEntity.ok(eventService.updateEvent(id, event));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Event> getEvent(@PathVariable Long id) {
    return ResponseEntity.ok(eventService.getEvent(id));
  }

  @GetMapping
  public ResponseEntity<List<Event>> getAllEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
    eventService.deleteEvent(id);
    return ResponseEntity.noContent().build();
  }
}