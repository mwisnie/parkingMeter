package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.ParkingSpace;
import mw.zadanie.parkingmeter.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ParkingSpace currentSpace = getSpaceById(space.getId());
        if (currentSpace == null) {
            // no space with such id
            // todo: specific exception
            return null;
        }

        if (space.getName() != null) {
            currentSpace.setName(space.getName());
        }
        if (space.getIsChangingMeter() != null && space.getIsChangingMeter().equals("true")) { // so meterOn not set to false, when this value is not present in JSON
            currentSpace.setMeterOn(space.isMeterOn());
        }
        if (space.getParkingSessions() != null) {
            currentSpace.setParkingSessions(space.getParkingSessions());
        }

        return repository.save(currentSpace);
    }

    @Override
    public void deleteSpaceById(Long id) {
        repository.deleteById(id);
    }

}
