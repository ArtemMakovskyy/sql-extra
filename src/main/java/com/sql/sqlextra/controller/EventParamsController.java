package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EventParamsDTO;
import com.sql.sqlextra.entity.EventParamsId;
import com.sql.sqlextra.service.EventParamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/event-params")
@RequiredArgsConstructor
public class EventParamsController {

    private final EventParamsService service;

    @GetMapping
    public List<EventParamsDTO> findAll() {
        return service.findAll();
    }

    @PostMapping
    public EventParamsDTO create(@RequestBody EventParamsDTO eventParamsDTO) {
        return service.save(eventParamsDTO);
    }

    @GetMapping("/session/{gaSessionId}")
    public List<EventParamsDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}
