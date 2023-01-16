package com.virtual.powerplant.controllers;

import com.virtual.powerplant.config.ITConfiguration;
import com.virtual.powerplant.dtos.BatteryCreationDTO;
import com.virtual.powerplant.exceptions.BadRequestException;
import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.repositories.BatteryRepository;
import com.virtual.powerplant.utils.BatteryConstants;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ITConfiguration.class})
class BatteryControllerTest {

    private final BatteryController batteryController;

    private final BatteryRepository batteryRepository;

    @Autowired
    public BatteryControllerTest(BatteryController batteryController, BatteryRepository batteryRepository) {
        this.batteryController = batteryController;
        this.batteryRepository = batteryRepository;
    }

    private final List<Battery> batteries = new ArrayList<>();

    private static final Sort SORT_WATT = Sort.by(BatteryConstants.WATT);

    @BeforeEach
    public void setup() {
        batteries.add(buildBatteryDTO("batteryA", 1L, 1));
        batteries.add(buildBatteryDTO("batteryB", 2L, 2));
        batteries.add(buildBatteryDTO("batteryC", 3L, 3));
        batteryRepository.saveAll(batteries);
    }

    @Test
    void testSaveAll_shouldPersistAllBatteriesInDatabase() {
        // given
        BatteryCreationDTO battery3 = new BatteryCreationDTO();

        // when
        var response = batteryController.saveAll(List.of(battery3));

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        var persistedBatteries = batteryRepository.findAll();
        assertEquals(4, persistedBatteries.size());
        assertTrue(response.getBody().getData().stream().allMatch(battery -> Objects.nonNull(battery.getId())));
    }

    @Test
    void testFetchAll_withIncludeMetaDataFlagSetAsTrue_shouldAppendMetaDataInResult() {
        // when
        var response = batteryController
                .fetchAll(null, null, SORT_WATT, true);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getData().size());
        var metadata = response.getBody().getMetadata();
        assertNotNull(metadata);
        assertEquals(6L, metadata.get(BatteryConstants.TOTAL_WATT_CAPACITY));
        assertEquals(2.0F, metadata.get(BatteryConstants.AVERAGE_WATT_CAPACITY));
    }

    @Test
    void testFetchAll_withIncludeMetaDataFlagSetAsFalse_shouldNotAppendMetaDataInResult() {
        // when
        var response = batteryController
                .fetchAll(null, null, SORT_WATT, false);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getData().size());
        assertNull(response.getBody().getMetadata());
    }

    @Test
    void testFetchAll_withMinPostCodeGreaterThanMaxPostCode_shouldThrowBadRequestException() {
        // given
        Long minPostCode = 2L;
        Long maxPostCode = 1L;

        // when and then
        assertThrows(BadRequestException.class, () -> batteryController
                .fetchAll(minPostCode, maxPostCode, SORT_WATT, false));
    }

    @Test
    void testFetchAll_withMinPostCodeLesserThanMaxPostCodeWithSortFieldNameAsc_shouldFetchBatteryListInThePostCodeRangeOrderedByName() {
        // given
        Long minPostCode = 1L;
        Long maxPostCode = 2L;

        // when
        var response = batteryController
                .fetchAll(minPostCode, maxPostCode, Sort.by(BatteryConstants.NAME), false);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var fetchedBatteries = response.getBody().getData();
        assertEquals(2, fetchedBatteries.size());
        assertEquals(batteries.get(0).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(1).getName());
    }

    @Test
    void testFetchAll_withNonNullMinPostCodeAndNullMaxPostCodeWithSortFieldNameAsc_shouldFetchBatteriesWithHigherPostCodeOrderedByName() {
        // given
        Long minPostCode = 2L;

        // when
        var response = batteryController
                .fetchAll(minPostCode, null, Sort.by(BatteryConstants.NAME), false);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var fetchedBatteries = response.getBody().getData();
        assertEquals(2, fetchedBatteries.size());
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(2).getName(), fetchedBatteries.get(1).getName());
    }

    @Test
    void testFetchAll_withNullMinPostCodeAndNonNullMaxPostCodeAndSortByName_shouldFetchBatteriesWithLowerPostCodeOrderedByName() {
        // given
        Long maxPostCode = 2L;

        // when
        var response = batteryController
                .fetchAll(null, maxPostCode, Sort.by(Sort.Direction.ASC, BatteryConstants.NAME), false);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var fetchedBatteries = response.getBody().getData();
        assertEquals(2, fetchedBatteries.size());
        assertEquals(batteries.get(0).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(1).getName());
    }

    @Test
    void testFetchAll_withNullMinPostCodeAndNullMaxPostCodeWithSortFieldNameAsc_shouldFetchAllBatteriesOrderedByName() {
        // when
        var response = batteryController
                .fetchAll(null, null, Sort.by(BatteryConstants.NAME), false);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var fetchedBatteries = response.getBody().getData();
        assertEquals(3, fetchedBatteries.size());
        assertEquals(batteries.get(0).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(1).getName());
        assertEquals(batteries.get(2).getName(), fetchedBatteries.get(2).getName());
    }

    @AfterEach
    public void tearDown() {
        batteryRepository.deleteAll();
    }

    private static Battery buildBatteryDTO(String name, Long postCode, Integer wattCapacity) {
        Battery battery = new Battery(name, postCode, wattCapacity);
        battery.setName(name);
        battery.setPostCode(postCode);
        battery.setWattCapacity(wattCapacity);
        return battery;
    }
}
