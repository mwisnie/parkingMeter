package mw.zadanie.parkingmeter.service;

import java.util.Date;

public interface CostCalculationService {
    Double calculateCostForSession(Long sessionId);
    Double calculateEarningsForPeriod(Date start, Date end);
    Double calculateAllEarnings();
}
