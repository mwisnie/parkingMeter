package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.ParkingSession;

import java.util.List;

public interface ParkingSessionService {
    List<ParkingSession> getAllSessions();
    ParkingSession getSessionById(Long sessionId);
    List<ParkingSession> getAllSessionsForSpace(Long spaceId);
    ParkingSession getSessionByIdForSpace(Long spaceId, Long sessionId);
    ParkingSession createSessionForSpace(Long spaceId, ParkingSession session);
    ParkingSession updateSessionForSpace(Long spaceId, ParkingSession session);
    void deleteSessionById(Long spaceId, Long id);
}
