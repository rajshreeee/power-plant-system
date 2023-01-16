package com.virtual.powerplant.serviceimpls;

import com.virtual.powerplant.service.BatteryService;
import com.virtual.powerplant.validations.BatteryValidationService;
import com.virtual.powerplant.utils.BatteryConstants;
import com.virtual.powerplant.mappers.BatteryMapper;
import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.dtos.BatteryCreationDTO;
import com.virtual.powerplant.repositories.BatteryRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Slf4j
@Service
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository batteryRepository;

    private final BatteryValidationService batteryValidationService;

    private final BatteryMapper batteryMapper;

    @Override
    public List<Battery> saveAll(List<BatteryCreationDTO> batteryCreationDTOs) {
        List<Battery> batteries = batteryCreationDTOs.stream()
                .map(batteryMapper::toBattery)
                .toList();
        log.info("Creating batteries.");
        return batteryRepository.saveAll(batteries);
    }

    @Override
    public List<Battery> findAll(Long minPostCode, Long maxPostCode, Sort sort) {
        var isPostCodeRangeProvided = Objects.nonNull(minPostCode) && Objects.nonNull(maxPostCode);
        if (isPostCodeRangeProvided) {
            return findByRangeOfPostCode(minPostCode, maxPostCode, sort);
        } else if (Objects.nonNull(minPostCode)) {
            return batteryRepository.findByPostCodeIsGreaterThanEqual(minPostCode, sort);
        } else if (Objects.nonNull(maxPostCode)) {
            return batteryRepository.findByPostCodeIsLessThanEqual(maxPostCode, sort);
        }
        return batteryRepository.findAll(sort);
    }

    private List<Battery> findByRangeOfPostCode(Long minPostCode, Long maxPostCode, Sort sort) {
        batteryValidationService.validatePostCodeRange(minPostCode, maxPostCode);
        return batteryRepository.findByPostCodeBetween(Range.closed(minPostCode, maxPostCode), sort);
    }

    @Override
    public Map<String, Object> getMetaData(@Nonnull List<Battery> batteries) {
        Map<String, Object> metaData = new HashMap<>();
        IntSummaryStatistics stats = batteries.stream()
                .filter(battery -> Objects.nonNull(battery.getWattCapacity()))
                .mapToInt(Battery::getWattCapacity).summaryStatistics();
        metaData.put(BatteryConstants.TOTAL_WATT_CAPACITY, stats.getSum());
        metaData.put(BatteryConstants.AVERAGE_WATT_CAPACITY, batteries.isEmpty()
                ? 0
                : (float) stats.getSum() / batteries.size());
        return metaData;
    }
}
