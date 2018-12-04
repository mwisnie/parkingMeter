package mw.zadanie.parkingmeter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import mw.zadanie.parkingmeter.model.ParkingSpace
import mw.zadanie.parkingmeter.repository.ParkingSpaceRepository
import mw.zadanie.parkingmeter.service.ParkingSpaceServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebMvcTest(controllers = [ParkingSpaceController])
class ParkingSpaceControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ParkingSpaceServiceImpl spaceService

    @Autowired
    private ObjectMapper objectMapper

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        ParkingSpaceServiceImpl parkingSpaceServiceImpl() {
            return detachedMockFactory.Stub(ParkingSpaceServiceImpl)
        }

        @Bean
        ParkingSpaceRepository parkingSpaceRepository() {
            return detachedMockFactory.Stub(ParkingSpaceRepository)
        }
    }

    def "GET with specific id should return ParkingSpace instance / via ResultMatchers"() {
        given:
            spaceService.getSpaceById(1) >> new ParkingSpace(1, "spaceName", true, null, null)
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/api/spaces/1"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
            result.andExpect(MockMvcResultMatchers.jsonPath('$.name').value('spaceName'))
            result.andExpect(MockMvcResultMatchers.jsonPath('$.meterOn').value(true))
            result.andExpect(MockMvcResultMatchers.jsonPath('$.parkingSessions').value(null))
    }

    def "GET with specific id should return ParkingSpace instance / Spock way"() {
        given:
            spaceService.getSpaceById(1) >> new ParkingSpace(1, "spaceName", true, null, null)
        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/spaces/1")).andReturn().response
        then:
            response.status == HttpStatus.OK.value()
            with (objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 1
                it.name == "spaceName"
                it.meterOn == true
            }
    }

    def "POST with defined JSON should return ParkingSpace instance"() {
        given:
            def requestPayload = """{
                "name": "spaceName2",
                "meterOn": true
                }"""
            ParkingSpace testSpaceForStub = new ParkingSpace.ParkingSpaceBuilder().name("spaceName2").meterOn(true).build()
            spaceService.createSpace(testSpaceForStub) >> new ParkingSpace(111, "spaceName2", true, null, null)
        when:
            def response = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/spaces/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestPayload)).andReturn().response
        then:
            response.status == 200
            with(objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 111
                it.name == "spaceName2"
                it.meterOn == true
            }
    }

    def "PUT with defined JSON should return modified ParkingSpace instance"() {
        given:
            def requestPayload = """{
                    "id": 3,
                    "name": "changedName",
                    "meterOn": false
                    }"""
            ParkingSpace testSpaceForStub = new ParkingSpace.ParkingSpaceBuilder().id(3).name("changedName").meterOn(false).build()
            spaceService.updateSpace(testSpaceForStub) >> new ParkingSpace(4, "nameWasChanged", false, null, null)
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/spaces/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload)).andReturn().response
        then:
            response.status == 200
            with(objectMapper.readValue(response.contentAsString, Map)) {
                it.id == 4
                it.name == "nameWasChanged"
                it.meterOn == false
            }
    }

}
