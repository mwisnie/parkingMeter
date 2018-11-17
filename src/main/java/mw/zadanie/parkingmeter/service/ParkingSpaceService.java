package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.ParkingSpace;

import java.util.List;

public interface ParkingSpaceService {
    List<ParkingSpace> getAllSpaces();
    ParkingSpace getSpaceById(Long id);
    ParkingSpace createSpace(ParkingSpace space);
    ParkingSpace updateSpace(ParkingSpace space);
    void deleteSpaceById(Long id);
}
