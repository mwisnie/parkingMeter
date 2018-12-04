package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.ParkingSession;
import mw.zadanie.parkingmeter.model.ParkingSpace;
import mw.zadanie.parkingmeter.repository.ParkingSessionRepository;
import mw.zadanie.parkingmeter.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    @Autowired
    private ParkingSessionRepository sessionRepository;

    @Autowired
    private ParkingSpaceRepository spaceRepository;

    @Override
    public List<ParkingSession> getAllSessionsForSpace(Long spaceId) {
        ParkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        return space != null ? space.getParkingSessions() : null;
    }

    @Override
    public ParkingSession getSessionByIdForSpace(Long spaceId, Long sessionId) {
        ParkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        if (space == null) {
            return null;
        }
        return space.getParkingSessions().stream().filter(session -> session.getId().equals(sessionId))
                .findFirst().orElse(null);
    }

    @Override
    public List<ParkingSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public ParkingSession getSessionById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public ParkingSession createSessionForSpace(Long spaceId, ParkingSession parkingSession) {
        ParkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        if (space == null) {
            // space doesn't exist
            // todo: detailed exception
            return null;
        }
        parkingSession.setParkingSpace(space);
        parkingSession.setStartTime(new Date());
        space.setMeterOn(true);
        spaceRepository.save(space);
        return sessionRepository.save(parkingSession);
    }

    @Override
    public ParkingSession updateSessionForSpace(Long spaceId, ParkingSession parkingSession) {
        ParkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        if (space == null) {
            // space doesn't exist
            // todo: detailed exception
            return null;
        }
        ParkingSession currentSession = space.getParkingSessions().stream()
                .filter(sess -> sess.getId() == parkingSession.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Space doesn't have session with specified id")); // todo: specific exception

        // change only fields present in JSON
        if (parkingSession.getEndTime() != null) {
            currentSession.setEndTime((parkingSession.getEndTime()));
        }
        if (parkingSession.getCarRegistration() != null) {
            currentSession.setCarRegistration((parkingSession.getCarRegistration()));
        }
        if (parkingSession.getTariff() != null) {
            currentSession.setTariff((parkingSession.getTariff()));
        }
        return sessionRepository.save(currentSession);
    }

    @Override
    public void deleteSessionById(Long spaceId, Long sessionId) {
        ParkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        if (space != null) {
            ParkingSession sessionToDelete = space.getParkingSessions().stream()
                    .filter(sess -> sess.getId().equals(sessionId))
                    .findFirst().orElse(null);
            if (sessionToDelete != null) {
                space.getParkingSessions().remove(sessionToDelete);
                spaceRepository.save(space);
                sessionRepository.deleteById(sessionToDelete.getId());
            }
        }
    }

}
