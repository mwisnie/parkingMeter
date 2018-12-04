package mw.zadanie.parkingmeter.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpace {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private boolean meterOn;

    @Transient // helper so meterOn is not set to false if JSON doesn't have it (ParkingSpaceServiceImpl)
    private String isChangingMeter;

    @JsonManagedReference
    @OneToMany(mappedBy = "parkingSpace", cascade = CascadeType.ALL)
    private List<ParkingSession> parkingSessions;

    public void addSession(ParkingSession session) {
            if (parkingSessions == null || parkingSessions.isEmpty()) {
            parkingSessions = new ArrayList<>();
        }
        parkingSessions.add(session);
    }

}
