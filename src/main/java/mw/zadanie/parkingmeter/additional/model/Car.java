package mw.zadanie.parkingmeter.additional.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mw.zadanie.parkingmeter.model.DriverType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    private String registration;

    private DriverType driverType;

}
