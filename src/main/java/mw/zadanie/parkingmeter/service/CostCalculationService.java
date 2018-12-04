package mw.zadanie.parkingmeter.service;

public interface CostCalculationService {
    Double calculateCostForSession(Long sessionId);
    Double calculateEarningsForDay(String dateString);
    Double calculateTotalEarnings();
}
