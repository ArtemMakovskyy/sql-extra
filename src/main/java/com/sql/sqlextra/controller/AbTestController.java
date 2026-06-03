package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.AbTestDTO;
import com.sql.sqlextra.entity.AbTestId;
import com.sql.sqlextra.service.AbTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ab-tests")
@RequiredArgsConstructor
public class AbTestController {

    private final AbTestService service;

    @GetMapping
    public List<AbTestDTO> findAll() {
        return service.findAll();
    }

    @PostMapping
    public AbTestDTO create(@RequestBody AbTestDTO abTestDTO) {
        return service.save(abTestDTO);
    }

    @GetMapping("/session/{gaSessionId}")
    public List<AbTestDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}
