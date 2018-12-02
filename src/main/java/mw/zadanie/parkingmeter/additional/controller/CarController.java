package mw.zadanie.parkingmeter.additional.controller;

import mw.zadanie.parkingmeter.additional.model.Car;
import mw.zadanie.parkingmeter.additional.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/cars", "/api/cars/"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CarController {

    @Autowired
    private CarServiceImpl carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@RequestParam("id") Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping
    public Car updateCar(@RequestBody Car car) {
        return carService.updateCar(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@RequestParam("id") Long id) {
        carService.deleteCarById(id);
    }


}
