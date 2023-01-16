package com.virtual.powerplant.mappers;

import com.virtual.powerplant.dtos.BatteryCreationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BatteryMapperTest {

    @InjectMocks
    private BatteryMapper batteryMapper;

    @Test
    void testToBattery_shouldConvertBatteryCreationDTOToBattery() {
        // given
        BatteryCreationDTO batteryCreationDTO = new BatteryCreationDTO();
        batteryCreationDTO.setName("A1");
        batteryCreationDTO.setPostCode(1L);
        batteryCreationDTO.setWattCapacity(10);

        // when
        var battery = batteryMapper.toBattery(batteryCreationDTO);

        // then
        Assertions.assertEquals(batteryCreationDTO.getName(), battery.getName());
        Assertions.assertEquals(batteryCreationDTO.getPostCode(), battery.getPostCode());
        Assertions.assertEquals(batteryCreationDTO.getWattCapacity(), battery.getWattCapacity());
    }
}
