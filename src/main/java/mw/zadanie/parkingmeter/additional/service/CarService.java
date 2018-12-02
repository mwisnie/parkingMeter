package mw.zadanie.parkingmeter.additional.service;

import mw.zadanie.parkingmeter.additional.model.Car;

import java.util.List;

public interface CarService {
    Car getCarById(Long id);
    List<Car> getAllCars();
    Car createCar(Car car);
    Car updateCar(Car car);
    void deleteCarById(Long id);
}
