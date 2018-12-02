package mw.zadanie.parkingmeter.service;

import mw.zadanie.parkingmeter.model.DriverType;
import mw.zadanie.parkingmeter.model.ParkingSession;
import mw.zadanie.parkingmeter.repository.ParkingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CostCalculationServiceImpl implements CostCalculationService {

    @Autowired
    private ParkingSessionRepository sessionRepository;


    private static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    @Override
    public Double calculateCostForSession(Long sessionId) {
        ParkingSession session = sessionRepository.findById(sessionId).orElse(null);
        return session != null ? calculateCost(session) : null;
    }

    @Override
    public Double calculateEarningsForDay(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date periodStart = null;
        try {
            periodStart = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // todo: properly handle
            e.printStackTrace();
        }

        double totalEarnings = 0;
        List<ParkingSession> sessions = sessionRepository.findAll();

        if (sessions.isEmpty() || periodStart == null) {
            return totalEarnings;
        }

        int dayInMs = 86400000;
        for (ParkingSession session: sessions) {
            if (session.getEndTime() != null
                    && session.getEndTime().getTime() > periodStart.getTime()
                    && session.getEndTime().getTime() < periodStart.getTime() + dayInMs) {
                totalEarnings += calculateCost(session);
            }
        }
        return totalEarnings;
    }

    @Override
    public Double calculateTotalEarnings() {
        double totalEarnings = 0;

        List<ParkingSession> sessions = sessionRepository.findAll();
        for (ParkingSession s: sessions) {
            totalEarnings += calculateCost(s);
        }
        return totalEarnings;
    }

    private double calculateCost(ParkingSession session) {
        long startTime = session.getStartTime().getTime();
        long endTime = session.getEndTime() != null ? session.getEndTime().getTime() : new Date().getTime();
        long timeDiffInMs = endTime - startTime;
        long timeDiffInHours = TimeUnit.HOURS.convert(timeDiffInMs, TimeUnit.MILLISECONDS);

        DriverType tariff = session.getTariff();

        double cost;
        if (tariff == DriverType.DISABLED) {
            cost = calculateCostForDisabled(timeDiffInHours);
        } else {
            cost = calculateCostForRegular(timeDiffInHours);
        }
        return cost;
    }

    private double calculateCostForDisabled(Long timeDiffInHours) {
        double totalCost = 0;

        if (timeDiffInHours == 1) {
            totalCost += 1;
        } else if (timeDiffInHours > 1) {
            totalCost += 1;

            int remainingHours = (int) (timeDiffInHours - 1);
            double tempHourCost = 1;

            for (int hour = 0; hour < remainingHours; hour++) {
                tempHourCost = 1.2 * tempHourCost;
                totalCost += tempHourCost;
            }

        }
        return roundToTwoDecimalPlaces(totalCost);
    }

    private double calculateCostForRegular(Long timeDiffInHours) {
        double totalCost = 1;

        if (timeDiffInHours == 1) {
            totalCost += 2;

        } else if (timeDiffInHours > 1) {
            totalCost += 2;

            int remainingHours = (int) (timeDiffInHours - 1);
            double tempHourCost = 2;

            for (int hour = 0; hour < remainingHours; hour++) {
                tempHourCost = 1.5 * tempHourCost;
                totalCost += tempHourCost;
            }
        }
        return roundToTwoDecimalPlaces(totalCost);
    }

}
