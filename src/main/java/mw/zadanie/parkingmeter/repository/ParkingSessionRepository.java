package mw.zadanie.parkingmeter.repository;

import mw.zadanie.parkingmeter.model.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
}
