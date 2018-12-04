package mw.zadanie.parkingmeter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSession {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonBackReference
    private ParkingSpace parkingSpace;

    private String carRegistration;

    private DriverType tariff;

    private Date startTime;

    private Date endTime;

    private boolean finished;

}
