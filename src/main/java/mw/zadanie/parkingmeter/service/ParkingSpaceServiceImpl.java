package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.ParkingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mw.zadanie.parkingmeter.repository.ParkingSpaceRepository;

import java.util.List;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository repository;

    @Override
    public List<ParkingSpace> getAllSpaces() {
        return repository.findAll();
    }

    @Override
    public ParkingSpace getSpaceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ParkingSpace createSpace(ParkingSpace space) {
        return repository.save(space);
    }

    @Override
    public ParkingSpace updateSpace(ParkingSpace space) {
        return getSpaceById(space.getId()) != null ? repository.save(space) : null;
    }

    @Override
    public void deleteSpaceById(Long id) {
        repository.deleteById(id);
    }

}
