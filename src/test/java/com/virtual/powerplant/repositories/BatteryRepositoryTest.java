package com.virtual.powerplant.repositories;

import com.virtual.powerplant.config.TestConfiguration;
import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.utils.BatteryConstants;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest(excludeAutoConfiguration = AutoConfiguration.class)
@ImportAutoConfiguration(classes = {TestConfiguration.class})
class BatteryRepositoryTest {

    private final BatteryRepository batteryRepository;

    @Autowired
    public BatteryRepositoryTest(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    private final List<Battery> batteries = new ArrayList<>();

    private static final Sort SORT_NAME_ASC = Sort.by(Sort.Direction.ASC, BatteryConstants.NAME);

    @BeforeEach
    public void setup() {
        batteries.add(new Battery("powerA", 100L, 1));
        batteries.add(new Battery("powerB", 200L, 2));
        batteries.add(new Battery("powerC", 300L, 2));
        batteryRepository.saveAll(batteries);
    }
    @Test
    void testFindByPostCodeRangeBetween_withNoSortSpecified_shouldFetchAllBatteriesThatLieWithinThePostCodeRange() {
        //given
        Long minPostCode = 100L;
        Long maxPostCode = 200L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeBetween(Range.closed(minPostCode, maxPostCode), null);

        // then
        assertEquals(2, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, minPostCode, maxPostCode));
    }
    @Test
    void testFindByPostCodeRangeBetween_withSortByNameAsc_shouldFetchAllBatteriesThatLieWithinThePostCodeRangeOrderedByNameAsc() {
        //given
        Long minPostCode = 200L;
        Long maxPostCode = 300L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeBetween(Range.closed(minPostCode, maxPostCode), SORT_NAME_ASC);

        // then
        assertEquals(2, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, minPostCode, maxPostCode));
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(2).getName(), fetchedBatteries.get(1).getName());
    }
    @Test
    void testFindByPostCodeIsGreaterThanEqual_withSortByName_shouldFetchAllBatteriesThatHaveHigherPostCodeRangeOrderedByNameAsc() {
        //given
        Long minPostCode = 150L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeIsGreaterThanEqual(minPostCode, SORT_NAME_ASC);

        // then
        assertEquals(2, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, minPostCode, null));
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(2).getName(), fetchedBatteries.get(1).getName());
    }
    @Test
    void testFindByPostCodeIsGreaterThanEqual_withNoSortSpecified_shouldFetchAllBatteriesThatHaveHigherPostCodeRange() {
        //given
        Long minPostCode = 300L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeIsGreaterThanEqual(minPostCode, null);

        // then
        assertEquals(1, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, minPostCode, null));
    }

    @Test
    void testFindByPostCodeIsLessThanEqual_withNoSortSpecified_shouldFetchAllBatteriesThatHaveLowerPostCodeRange() {
        //given
        Long maxPostCode = 300L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeIsLessThanEqual(maxPostCode, null);

        // then
        assertEquals(3, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, null, maxPostCode));
    }

    @Test
    void testFindByPostCodeIsLessThanEqual_withSortByName_shouldFetchAllBatteriesThatHaveLowerPostCodeRangeOrderedByNameAsc() {
        //given
        Long maxPostCode = 250L;

        // when
        var fetchedBatteries = batteryRepository
                .findByPostCodeIsLessThanEqual(maxPostCode, SORT_NAME_ASC);

        // then
        assertEquals(2, fetchedBatteries.size());
        assertTrue(isBatteriesPostCodeWithinRange(fetchedBatteries, null, maxPostCode));
        assertEquals(batteries.get(0).getName(), fetchedBatteries.get(0).getName());
        assertEquals(batteries.get(1).getName(), fetchedBatteries.get(1).getName());
    }

    private boolean isBatteriesPostCodeWithinRange(List<Battery> batteries, Long minPostCode, Long maxPostCode) {
        if (Objects.nonNull(minPostCode) && Objects.nonNull(maxPostCode)) {
            return batteries.stream()
                    .allMatch(fetchedBattery ->
                            fetchedBattery.getPostCode() <= maxPostCode
                                    && fetchedBattery.getPostCode() >= minPostCode);
        } else if (Objects.nonNull(minPostCode)) {
            return batteries.stream()
                    .allMatch(fetchedBattery -> fetchedBattery.getPostCode() >= minPostCode);
        }
        return batteries.stream()
                .allMatch(fetchedBattery -> fetchedBattery.getPostCode() <= maxPostCode);
    }

    @AfterEach
    public void tearDown() {
        batteryRepository.deleteAll();
    }
}
