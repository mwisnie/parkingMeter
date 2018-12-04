package mw.zadanie.parkingmeter.controller;

import mw.zadanie.parkingmeter.service.CostCalculationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4200/"}, maxAge = 3600)
public class UtilityController {

    @Autowired
    private CostCalculationServiceImpl costService;

    @GetMapping(path = {"/api/cost/{id}", "/api/cost/{id}/"})
    public Double getCostForSession(@PathVariable("id") Long id) {
        return costService.calculateCostForSession(id);
    }

    @GetMapping(path = {"/api/earnings", "/api/earnings/"})
    public Double getAllEarnings() {
        return costService.calculateTotalEarnings();
    }

    @GetMapping(path = {"/api/earnings/{date}", "/api/earnings/{date}/"})
    public Double getEarningsForDay(@PathVariable("date") String dateString) {
        return costService.calculateEarningsForDay(dateString);
    }

}
