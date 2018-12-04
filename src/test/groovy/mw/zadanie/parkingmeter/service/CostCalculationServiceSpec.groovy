package mw.zadanie.parkingmeter.service

import mw.zadanie.parkingmeter.model.DriverType
import mw.zadanie.parkingmeter.model.ParkingSession
import mw.zadanie.parkingmeter.repository.ParkingSessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat

@SpringBootTest
class CostCalculationServiceSpec extends Specification {

    @Autowired
    private CostCalculationServiceImpl costService

    ParkingSessionRepository repository = Mock()

    def setup() {
        costService.sessionRepository = repository
    }

    @Unroll
    def "calculateCost properly calculates parking cost for Regular and Disabled drivers"() {
        given:
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy")

        when:
            def tempSession = new ParkingSession.ParkingSessionBuilder()
                    .startTime(dateFormat.parse(startTime))
                    .endTime(dateFormat.parse(endTime))
                    .tariff(tariff)
                    .build()
        then:
            expectedCost == costService.calculateCost(tempSession)

        where:
            startTime               | endTime                | tariff                | expectedCost
            "10:00 01-10-2018"      | "10:30 01-10-2018"     | DriverType.REGULAR    | 1.0  // during 1st hour: should pay 1
            "10:00 01-10-2018"      | "10:59 01-10-2018"     | DriverType.REGULAR    | 1.0  // during 1st hour: should pay 1
            "10:00 01-10-2018"      | "11:00 01-10-2018"     | DriverType.REGULAR    | 3.0  // starting 2nd hour: should pay 1 + 2 = 3
            "10:00 01-10-2018"      | "11:45 01-10-2018"     | DriverType.REGULAR    | 3.0  // during 2nd hour: should pay 1 + 2 = 3
            "10:00 01-10-2018"      | "12:00 01-10-2018"     | DriverType.REGULAR    | 6.0  // starting 3rd hour: should pay 1 + 2 + 1.5*2 = 6
            "10:00 01-10-2018"      | "13:00 01-10-2018"     | DriverType.REGULAR    | 10.5  // starting 4th hour: should pay 1 + 2 + 1.5*2 + 1.5*1.5*2 = 10.5
            "10:00 01-10-2018"      | "14:00 01-10-2018"     | DriverType.REGULAR    | 17.25  // starting 5th hour: should pay 1 + 2 + 1.5*2 + 1.5*1.5*2 + 2*1.5**3 = 10.5 + 6.65 = 17.25
            "10:00 01-10-2018"      | "15:00 01-10-2018"     | DriverType.REGULAR    | 27.37  // starting 6th hour: should pay 1 + 2 + 1.5*2 + 1.5*1.5*2 + 2*1.5**4 = 10.5 + 6.65 + 10.125 = 27.375 => 27.37
            "10:00 01-10-2018"      | "10:30 01-10-2018"     | DriverType.DISABLED   | 0.0  // during 1st hour: stay is free
            "10:00 01-10-2018"      | "10:59 01-10-2018"     | DriverType.DISABLED   | 0.0  // during 1st hour: stay is free
            "10:00 01-10-2018"      | "11:00 01-10-2018"     | DriverType.DISABLED   | 1.0  // starting 2nd hour: should pay 1
            "10:00 01-10-2018"      | "11:45 01-10-2018"     | DriverType.DISABLED   | 1.0  // during 2nd hour: should pay 1
            "10:00 01-10-2018"      | "12:00 01-10-2018"     | DriverType.DISABLED   | 2.2  // starting 3rd hour: should pay 1 + 1*1.2 = 2.2
            "10:00 01-10-2018"      | "13:00 01-10-2018"     | DriverType.DISABLED   | 3.64  // starting 4th hour: should pay 1 + 1*1.2 + 1*1.2**2 = 3.64
            "10:00 01-10-2018"      | "14:00 01-10-2018"     | DriverType.DISABLED   | 5.37  // starting 5th hour: should pay 1 + 1*1.2 + 1*1.2**2 + 1*1.2**3 = 5.37
            "10:00 01-10-2018"      | "15:00 01-10-2018"     | DriverType.DISABLED   | 7.44  // starting 6th hour: should pay 1 + 1*1.2 + 1*1.2**2 + 1*1.2**3 + 1*1.2**4 = 7.44
    }

    def "calculateEarningsForDay properly calculate earnings for certain day"() {
        given:
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy")

            List<ParkingSession> presetList = new ArrayList<>()

            ParkingSession s1 = new ParkingSession.ParkingSessionBuilder()  // should not be included in calculation for 02-10-2018
                    .startTime(dateFormat.parse("10:00 01-10-2018"))
                    .endTime(dateFormat.parse("10:30 01-10-2018"))
                    .tariff(DriverType.REGULAR)
                    .build()
            ParkingSession s2 = new ParkingSession.ParkingSessionBuilder()
                    .startTime(dateFormat.parse("10:00 02-10-2018"))
                    .endTime(dateFormat.parse("14:00 02-10-2018"))
                    .tariff(DriverType.REGULAR)
                    .build()
            ParkingSession s3 = new ParkingSession.ParkingSessionBuilder()
                    .startTime(dateFormat.parse("10:00 02-10-2018"))
                    .endTime(dateFormat.parse("12:00 02-10-2018"))
                    .tariff(DriverType.DISABLED)
                    .build()
            ParkingSession s4 = new ParkingSession.ParkingSessionBuilder() // should not be included in calculation for 02-10-2018
                    .startTime(dateFormat.parse("10:00 03-10-2018"))
                    .endTime(dateFormat.parse("15:00 03-10-2018"))
                    .tariff(DriverType.DISABLED)
                    .build()
            presetList.addAll([s1, s2, s3, s4])

            repository.findAll() >> presetList

        expect:
            costService.calculateCost(s1) == 1.0    // should not be included
            costService.calculateCost(s2) == 17.25
            costService.calculateCost(s3) == 2.20
            costService.calculateCost(s4) == 7.44   // should not be included

            costService.calculateEarningsForDay("02-10-2018") == 19.45
    }

}
