package com.example.pgdemo.service;

import com.example.pgdemo.model.Event;
import com.example.pgdemo.repository.EventRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EventService {
  private final EventRepository eventRepository;
  private static final String CACHE_NAME = "events";

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Caching(evict = {
          @CacheEvict(value = CACHE_NAME, key = "'all'"),
          @CacheEvict(value = CACHE_NAME, key = "#result.id")
  })
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }

  @Cacheable(value = CACHE_NAME, key = "#id")
  public Event getEvent(Long id) {
    return eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
  }

  @Caching(evict = {
          @CacheEvict(value = CACHE_NAME, key = "'all'"),
          @CacheEvict(value = CACHE_NAME, key = "#id")
  })
  public Event updateEvent(Long id, Event event) {
    Event existingEvent = getEvent(id);
    existingEvent.setTitle(event.getTitle());
    existingEvent.setDescription(event.getDescription());
    return eventRepository.save(existingEvent);
  }

  @Cacheable(value = CACHE_NAME, key = "'all'")
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  @Caching(evict = {
          @CacheEvict(value = CACHE_NAME, key = "'all'"),
          @CacheEvict(value = CACHE_NAME, key = "#id")
  })
  public void deleteEvent(Long id) {
    eventRepository.deleteById(id);
  }
}