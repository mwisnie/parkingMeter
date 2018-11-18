package mw.zadanie.parkingmeter.controller;

import mw.zadanie.parkingmeter.service.CostCalculationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilityController {

    @Autowired
    private CostCalculationServiceImpl costService;

    @GetMapping(path = {"/api/cost/{id}", "/api/cost/{id}/"})
    public Double getCostForSession(@PathVariable("id") Long id) {
        return costService.calculateCostForSession(id);
    }

    @GetMapping(path = {"/api/earnings", "/api/earnings/"})
    public Double getAllEarnings() {
        return costService.calculateAllEarnings();
    }

//    @GetMapping(path = {"/api/earnings", "/api/earnings/"})
//    public Double getEarningsForTimePeriod() {
//        return costService.calculateEarningsForPeriod(null, null);
//    }

}
