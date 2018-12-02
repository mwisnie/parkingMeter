package mw.zadanie.parkingmeter.additional.service;

import mw.zadanie.parkingmeter.additional.model.Car;
import mw.zadanie.parkingmeter.additional.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository repository;

    @Override
    public Car getCarById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Car> getAllCars() {
        return repository.findAll();
    }

    @Override
    public Car createCar(Car car) {
        return repository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        return getCarById(car.getId()) != null ? repository.save(car) : null;
    }

    @Override
    public void deleteCarById(Long id) {
        repository.deleteById(id);
    }
}
