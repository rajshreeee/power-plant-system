package com.virtual.powerplant.validations;

public interface BatteryValidationService {

    /**
     * Throws exception if minPostCode is greater than maxPostCode.
     * @param minPostCode - The minimum postCode.
     * @param maxPostCode - The maximum postCode.
     */
    void validatePostCodeRange(Long minPostCode, Long maxPostCode);
}
