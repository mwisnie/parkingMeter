package mw.zadanie.parkingmeter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import mw.zadanie.parkingmeter.model.ParkingSession
import mw.zadanie.parkingmeter.repository.ParkingSessionRepository
import mw.zadanie.parkingmeter.repository.ParkingSpaceRepository
import mw.zadanie.parkingmeter.service.ParkingSessionServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebMvcTest(controllers = [ParkingSessionController])
class ParkingSessionControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ParkingSessionServiceImpl sessionService

    @Autowired
    private ObjectMapper objectMapper

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        ParkingSessionServiceImpl parkingSessionServiceImpl() {
            return detachedMockFactory.Stub(ParkingSessionServiceImpl)
        }

        @Bean
        ParkingSessionRepository parkingSessionRepository() {
            return detachedMockFactory.Stub(ParkingSessionRepository)
        }

        @Bean
        ParkingSpaceRepository parkingSpaceRepository() {
            return detachedMockFactory.Stub(ParkingSpaceRepository)
        }
    }

    def "GET with specific id should return ParkingSession instance"() {
        given:
            sessionService.getSessionByIdForSpace(1, 1) >>
                new ParkingSession.ParkingSessionBuilder().id(1).carId(999).finished(false).build()
        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/spaces/1/sessions/1")).andReturn().response
        then:
            response.status == HttpStatus.OK.value()
            with (objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 1
                it.carId == 999
                it.finished == false
            }
    }

    def "POST with defined JSON should return ParkingSession instance"() {
        given:
            def requestPayload = """{
                "carId": 5,
                "finished": false
                }"""
            ParkingSession testSessionForStub = new ParkingSession.ParkingSessionBuilder().carId(5).finished(false).build()
            sessionService.createSessionForSpace(1, testSessionForStub) >>
                new ParkingSession.ParkingSessionBuilder().id(1).carId(5).finished(false).build()
        when:
            def response = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/spaces/1/sessions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload)).andReturn().response
        then:
            response.status == 200
            with(objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 1
                it.carId == 5
                it.finished == false
            }
    }

    def "PUT with defined JSON should return modified ParkingSession instance"() {
        given:
            def requestPayload = """{
                "id": 1,
                "carId": 5,
                "finished": true
                }"""
            ParkingSession testSessionForStub = new ParkingSession.ParkingSessionBuilder().id(1).carId(5).finished(true).build()
            sessionService.updateSessionForSpace(1, testSessionForStub) >>
                new ParkingSession.ParkingSessionBuilder().id(1).carId(5).finished(true).build()
        when:
            def response = mockMvc.perform(MockMvcRequestBuilders
                    .put("/api/spaces/1/sessions/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestPayload)).andReturn().response
        then:
            response.status == 200
            with(objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 1
                it.carId == 5
                it.finished == true
        }
    }

}
