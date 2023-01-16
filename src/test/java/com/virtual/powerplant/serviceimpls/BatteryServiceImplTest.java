package com.virtual.powerplant.serviceimpls;

import com.virtual.powerplant.dtos.BatteryCreationDTO;
import com.virtual.powerplant.mappers.BatteryMapper;
import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.repositories.BatteryRepository;
import com.virtual.powerplant.utils.BatteryConstants;
import com.virtual.powerplant.validations.BatteryValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatteryServiceImplTest {

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private BatteryValidationService batteryValidationService;

    @Mock
    private BatteryMapper batteryMapper;

    @InjectMocks
    private BatteryServiceImpl batteryService;

    private static final Long minPostCode = 1L;
    private static final Long maxPostCode = 5L;
    private static final Sort sort = Sort.by(BatteryConstants.POST_CODE);

    @Test
    void testSaveAll_shouldConvertBatteryCreationDTOsToBatteriesAndCallSaveAllRepositoryMethod() {
        // given
        BatteryCreationDTO batteryCreationDTO1 = new BatteryCreationDTO();
        BatteryCreationDTO batteryCreationDTO2 = new BatteryCreationDTO();
        Battery battery1 = new Battery("one", 1L, 1);
        Battery battery2 = new Battery("two", 2L, 2);

        // mock
        when(batteryMapper.toBattery(batteryCreationDTO1)).thenReturn(battery1);
        when(batteryMapper.toBattery(batteryCreationDTO2)).thenReturn(battery2);

        // when
        batteryService.saveAll(Arrays.asList(batteryCreationDTO1, batteryCreationDTO2));

        // then
        verify(batteryMapper).toBattery(batteryCreationDTO1);
        verify(batteryMapper).toBattery(batteryCreationDTO2);
        verify(batteryRepository).saveAll(anyList());
    }

    @Test
    void testFindAll_withMinPostCodeAndMaxPostCodeAndSort_shouldValidatePostCodeAndCallFindByRangeRepositoryMethod() {
        // when
        batteryService.findAll(minPostCode, maxPostCode, sort);

        // then
        verify(batteryValidationService).validatePostCodeRange(minPostCode, maxPostCode);
        verify(batteryRepository).findByPostCodeBetween(Range.closed(minPostCode, maxPostCode), sort);
    }

    @Test
    void testFindAll_withOnlyMinPostCodeAndSortProvided_shouldCallRepositoryMethod() {
        // when
        batteryService.findAll(minPostCode, null, sort);

        // then
        verify(batteryRepository).findByPostCodeIsGreaterThanEqual(minPostCode, sort);
    }

    @Test
    void testFindAll_withOnlyMaxPostCodeAndSortProvided_shouldCallLessThanRepositoryMethod() {
        // when
        batteryService.findAll(null, maxPostCode, sort);

        // then
        verify(batteryRepository).findByPostCodeIsLessThanEqual(maxPostCode, sort);
    }

    @Test
    void testFindAll_withOnlySortProvided_shouldCallRepositoryMethod() {
        // when
        batteryService.findAll(null, null, sort);

        // then
        verify(batteryRepository).findAll(sort);
    }

    @Test
    void testGetMetaData_withNonEmptyBatteryList_shouldAddCorrectAverageAndTotalWattCapacityInfo() {
        // given
        List<Battery> batteryList = Arrays.asList(
                new Battery("battery1", 1L, 100 ),
                new Battery("battery2", 2L, 150),
                new Battery("battery3", 3L, 200),
                new Battery("battery4", 4L,  null)
                );

        // when
        var metaData = batteryService.getMetaData(batteryList);

        // then
        assertEquals(450L, metaData.get(BatteryConstants.TOTAL_WATT_CAPACITY));
        assertEquals(112.5F, metaData.get(BatteryConstants.AVERAGE_WATT_CAPACITY));
    }

    @Test
    void testGetMetaData_withEmptyBatteryList_shouldAddAverageAndTotalWattCapacityInfoAsZero() {
        // given
        List<Battery> batteryList = Collections.emptyList();

        // when
        var metaData = batteryService.getMetaData(batteryList);

        // then
        assertEquals(0L, metaData.get(BatteryConstants.TOTAL_WATT_CAPACITY));
        assertEquals(0.0F, metaData.get(BatteryConstants.AVERAGE_WATT_CAPACITY));
    }

}
