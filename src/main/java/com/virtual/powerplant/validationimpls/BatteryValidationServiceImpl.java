package com.virtual.powerplant.validationimpls;

import com.virtual.powerplant.utils.BatteryConstants;
import com.virtual.powerplant.exceptions.BadRequestException;
import com.virtual.powerplant.validations.BatteryValidationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BatteryValidationServiceImpl implements BatteryValidationService {

    @Override
    public void validatePostCodeRange(Long minPostCode, Long maxPostCode) {
        if (minPostCode > maxPostCode) {
            log.error("Invalid PostCode range. minPostCode is greater than maxPostCode.");
            throw new BadRequestException(BatteryConstants.POST_CODE_RANGE_VALIDATION_MSG);
        }
    }
}
