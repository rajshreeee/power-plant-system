package com.virtual.powerplant.dtos;

import com.virtual.powerplant.utils.BatteryConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatteryCreationDTO {

    @NotBlank(message = BatteryConstants.NAME_NOT_BLANK_MSG)
    private String name;

    @Positive(message = BatteryConstants.POST_CODE_POSITIVE_MSG)
    private Long postCode;

    @Positive(message = BatteryConstants.WATT_CAPACITY_POSITIVE_MSG)
    private Integer wattCapacity;
}
