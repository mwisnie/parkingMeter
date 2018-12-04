package mw.zadanie.parkingmeter.controller;

import mw.zadanie.parkingmeter.model.ParkingSpace;
import mw.zadanie.parkingmeter.service.ParkingSpaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/spaces", "/api/spaces/"})
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4200/"}, maxAge = 3600)
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceServiceImpl spaceService;

    @GetMapping
    public List<ParkingSpace> getAllSpaces() {
        return spaceService.getAllSpaces();
    }

    @GetMapping("/{id}")
    public ParkingSpace getSpaceById(@PathVariable Long id) {
        return spaceService.getSpaceById(id);
    }

    @PostMapping
    public ParkingSpace createSpace(@RequestBody ParkingSpace space) {
        return spaceService.createSpace(space);
    }

    @PutMapping
    public ParkingSpace updateSpace(@RequestBody ParkingSpace space) {
        return spaceService.updateSpace(space);
    }

    @DeleteMapping("/{id}")
    public void deleteSpaceById(@PathVariable Long id) {
        spaceService.deleteSpaceById(id);
    }

}
