package mw.zadanie.parkingmeter.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CostCalculationServiceImpl implements CostCalculationService {

    @Override
    public Double calculateCostForSession(Long sessionId) {
        return null;
    }

    @Override
    public Double calculateEarningsForPeriod(Date start, Date end) {
        return null;
    }

    @Override
    public Double calculateAllEarnings() {
        return null;
    }

}
