package com.virtual.powerplant.mappers;

import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.dtos.BatteryCreationDTO;
import org.springframework.stereotype.Component;

@Component
public class BatteryMapper {

    public Battery toBattery(BatteryCreationDTO batteryCreationDTO) {
        return new Battery(batteryCreationDTO.getName(), batteryCreationDTO.getPostCode(),
                batteryCreationDTO.getWattCapacity());
    }
}
