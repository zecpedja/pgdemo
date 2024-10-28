package com.example.pgdemo.service;

import com.example.pgdemo.model.Event;
import com.example.pgdemo.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {
  private final EventRepository eventRepository;

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }

  public Event updateEvent(Long id, Event event) {
    Event existingEvent = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

    existingEvent.setTitle(event.getTitle());
    existingEvent.setDescription(event.getDescription());

    return eventRepository.save(existingEvent);
  }

  public Event getEvent(Long id) {
    return eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
  }

  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  public void deleteEvent(Long id) {
    eventRepository.deleteById(id);
  }
}
