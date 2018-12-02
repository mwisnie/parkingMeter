package mw.zadanie.parkingmeter.repository

import mw.zadanie.parkingmeter.model.ParkingSession
import mw.zadanie.parkingmeter.model.ParkingSpace
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Shared
import spock.lang.Specification

@DataJpaTest
class ParkingSessionRepositorySpec extends Specification {

    @Autowired
    private ParkingSessionRepository sessionRepository

    @Autowired
    private ParkingSpaceRepository spaceRepository

    @Shared
    def sampleSession = new ParkingSession.ParkingSessionBuilder()
            .carRegistration("ABC")
            .build()

    def "modifying parkingSpace independently should be visible for binded parkingSession"() {
        given:
            def sampleSpace = new ParkingSpace.ParkingSpaceBuilder()
                    .name("space1")
                    .meterOn(true)
                    .build()
            def sessionWithParkingSpace = new ParkingSession.ParkingSessionBuilder()
                    .parkingSpace(sampleSpace)
                    .build()
            sessionWithParkingSpace.setParkingSpace(sampleSpace)
            def savedSpace = spaceRepository.save(sampleSpace)
            sessionRepository.save(sessionWithParkingSpace)
        when:
            def modifiedSpace = spaceRepository.findById(savedSpace.getId()).get()
            modifiedSpace.setName("modifiedName")
            spaceRepository.save(modifiedSpace)
        then:
            sessionRepository.findAll()[0].parkingSpace.getName().equals("modifiedName")
    }

    def "saved entity should be returned by id"() {
        given:
            def savedEntity = sessionRepository.save(sampleSession)
        when:
            def entityFromDb = sessionRepository.findById(savedEntity.getId())
        then:
            savedEntity.getId() == entityFromDb.get().getId()
    }

    def "findAll should return list of all entities"() {
        given:
            sessionRepository.save(sampleSession)
            sessionRepository.save(sampleSession)
        when:
            def entityList = sessionRepository.findAll()
        then:
            entityList.size() == 2
    }

    def "entity should be modified correctly"() {
        given:
            def savedEntity = sessionRepository.save(sampleSession)
        when:
            def entityFromDb = sessionRepository.findById(savedEntity.getId()).get()
            entityFromDb.setCarRegistration("ABC")
            sessionRepository.save(entityFromDb)
        then:
            sessionRepository.findById(savedEntity.getId()).get().getCarRegistration() == "ABC"
    }

    def "entity should be deleted correctly"() {
        given:
            def savedEntity = sessionRepository.save(sampleSession)
        when:
            sessionRepository.deleteById(savedEntity.getId())
        then:
            sessionRepository.findAll().size() == 0
    }

}
