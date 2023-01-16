package com.virtual.powerplant.validationimpls;

import com.virtual.powerplant.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BatteryValidationServiceImplTest {

    @InjectMocks
    private BatteryValidationServiceImpl batteryValidationService;

    @Test
    void testValidatePostCodeRange_withMinPostCodeGreaterThanMaxPostCode_shouldThrowBadRequestException() {
        // when and then
        assertThrows(BadRequestException.class, () -> batteryValidationService.validatePostCodeRange(2L, 1L));
    }

    @Test
    void testValidatePostCodeRange_withMinPostCodeEqualToMaxPostCode_shouldNotThrowBadRequestException() {
        // when and then
        assertDoesNotThrow(() -> batteryValidationService.validatePostCodeRange(2L, 3L));
    }

}
