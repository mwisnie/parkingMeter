package mw.zadanie.parkingmeter.repository

import mw.zadanie.parkingmeter.model.ParkingSpace
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Shared
import spock.lang.Specification

@DataJpaTest
class ParkingSpaceRepositorySpec extends Specification {

    @Autowired
    private ParkingSpaceRepository repository

    @Shared
    def sampleSpace = new ParkingSpace.ParkingSpaceBuilder()
        .name("space1")
        .meterOn(true)
        .build()


    def "saved entity should be returned by id"() {
        given:
            def savedEntity = repository.save(sampleSpace)
        when:
            def entityFromDb = repository.findById(savedEntity.getId())
        then:
            savedEntity.getId() == entityFromDb.get().getId()
    }

    def "findAll should return list of all entities"() {
        given:
            repository.save(sampleSpace)
            repository.save(sampleSpace)
        when:
            def entityList = repository.findAll()
        then:
            entityList.size() == 2
        }

    def "entity should be modified correctly"() {
        given:
            def savedEntity = repository.save(sampleSpace)
        when:
            def entityFromDb = repository.findById(savedEntity.getId()).get()
            entityFromDb.setName("changedName")
            repository.save(entityFromDb)
        then:
            repository.findById(savedEntity.getId()).get().getName().equals("changedName")
    }

    def "entity should be deleted correctly"() {
        given:
            def savedEntity = repository.save(sampleSpace)
        when:
            repository.deleteById(savedEntity.getId())
        then:
            repository.findAll().size() == 0
    }

}
