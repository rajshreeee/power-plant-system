package com.virtual.powerplant.service;

import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.dtos.BatteryCreationDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface BatteryService {

    List<Battery> saveAll(List<BatteryCreationDTO> batteries);

    /**
     * Finds all the batteries that match with the provided parameters.
     *
     * @param minPostCode - The minimum postCode.Any battery that has a postCode lesser than it will not be fetched.
     * @param maxPostCode - The maximum postCode.Any battery that has a postCode greater than it will not be fetched.
     * @param sortField - The field to sort the batteries by.
     */
    List<Battery> findAll(Long minPostCode, Long maxPostCode, Sort sortField);

    /**
     * Returns the metadata about the list of batteries. Includes average wattCapacity and total wattCapacity.
     * @param batteries - List of batteries whose metadata is required.
     */
    Map<String, Object> getMetaData(List<Battery> batteries);
}
