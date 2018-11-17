package mw.zadanie.parkingmeter.controller;

import mw.zadanie.parkingmeter.model.ParkingSession;
import mw.zadanie.parkingmeter.service.ParkingSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/spaces/{spaceId}/sessions/", "/api/spaces/{spaceId}/sessions"})
public class ParkingSessionController {

    @Autowired
    private ParkingSessionServiceImpl sessionService;

    @GetMapping
    public List<ParkingSession> getAllSessionsForSpace(@PathVariable("spaceId") Long spaceId) {
        return sessionService.getAllSessionsForSpace(spaceId);
    }

    @GetMapping("/{sessionId}")
    public ParkingSession getSessionById(@PathVariable("spaceId") Long spaceId, @PathVariable("sessionId") Long sessionId) {
        return sessionService.getSessionByIdForSpace(spaceId, sessionId);
    }

    @PostMapping
    public ParkingSession createSessionForSpace(@PathVariable("spaceId") Long spaceId, @RequestBody ParkingSession session) {
        return sessionService.createSessionForSpace(spaceId, session);
    }

    @PutMapping
    public ParkingSession updateSessionForSpace(@PathVariable("spaceId") Long spaceId, @RequestBody ParkingSession session) {
        return sessionService.updateSessionForSpace(spaceId, session);
    }

    @DeleteMapping("/{sessionId}")
    public void deleteSessionById(@PathVariable("spaceId") Long spaceId, @PathVariable("sessionId") Long sessionId) {
        sessionService.deleteSessionById(spaceId, sessionId);
    }

}
