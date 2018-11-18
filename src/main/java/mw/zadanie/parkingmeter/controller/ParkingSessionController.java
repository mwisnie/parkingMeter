package mw.zadanie.parkingmeter.controller;

import mw.zadanie.parkingmeter.model.ParkingSession;
import mw.zadanie.parkingmeter.service.ParkingSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingSessionController {

    @Autowired
    private ParkingSessionServiceImpl sessionService;

    @GetMapping(path = {"/api/sessions", "/api/sessions/"})
    public List<ParkingSession> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping(path = {"/api/sessions/{id}", "/api/sessions/{id}/"})
    public ParkingSession getSessionById(@PathVariable("id") Long id) {
        return sessionService.getSessionById(id);
    }

    @GetMapping(path = {"/api/spaces/{spaceId}/sessions", "/api/spaces/{spaceId}/sessions/"})
    public List<ParkingSession> getAllSessionsForSpace(@PathVariable("spaceId") Long spaceId) {
        return sessionService.getAllSessionsForSpace(spaceId);
    }

    @GetMapping(path = {"/api/spaces/{spaceId}/sessions/{sessionId}", "/api/spaces/{spaceId}/sessions/{sessionId}"})
    public ParkingSession getSessionById(@PathVariable("spaceId") Long spaceId, @PathVariable("sessionId") Long sessionId) {
        return sessionService.getSessionByIdForSpace(spaceId, sessionId);
    }

    @PostMapping(path = {"/api/spaces/{spaceId}/sessions", "/api/spaces/{spaceId}/sessions/"})
    public ParkingSession createSessionForSpace(@PathVariable("spaceId") Long spaceId, @RequestBody ParkingSession session) {
        return sessionService.createSessionForSpace(spaceId, session);
    }

    @PutMapping(path = {"/api/spaces/{spaceId}/sessions", "/api/spaces/{spaceId}/sessions/"})
    public ParkingSession updateSessionForSpace(@PathVariable("spaceId") Long spaceId, @RequestBody ParkingSession session) {
        return sessionService.updateSessionForSpace(spaceId, session);
    }

    @DeleteMapping(path = {"/api/spaces/{spaceId}/sessions/{sessionId}", "/api/spaces/{spaceId}/sessions/{sessionId}"})
    public void deleteSessionById(@PathVariable("spaceId") Long spaceId, @PathVariable("sessionId") Long sessionId) {
        sessionService.deleteSessionById(spaceId, sessionId);
    }

}
