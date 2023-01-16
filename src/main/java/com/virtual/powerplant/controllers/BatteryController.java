package com.virtual.powerplant.controllers;

import com.virtual.powerplant.dtos.BatteryCreationDTO;
import com.virtual.powerplant.models.Battery;
import com.virtual.powerplant.result.Result;
import com.virtual.powerplant.service.BatteryService;
import com.virtual.powerplant.utils.BatteryConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(BatteryConstants.BASE_API_URL)
@AllArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    @PostMapping
    public ResponseEntity<Result<List<Battery>>> saveAll(@Valid @RequestBody List<BatteryCreationDTO> batteries) {
        var savedBatteries =  batteryService.saveAll(batteries);
        return new ResponseEntity<>(new Result.ResultBuilder<>(savedBatteries).build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Result<List<Battery>>> fetchAll(
            @RequestParam(required = false, name = BatteryConstants.MIN_POST_CODE) Long minPostCode,
            @RequestParam(required = false, name = BatteryConstants.MAX_POST_CODE) Long maxPostCode,
            Sort sort,
            @RequestParam(required = false, name = BatteryConstants.INCLUDE_META_DATA) boolean includeMetaData ) {
        var batteries = batteryService.findAll(minPostCode, maxPostCode, sort);
        var result = new Result.ResultBuilder<>(batteries);
        if (includeMetaData) {
            result.metaData(batteryService.getMetaData(batteries));
        }
        return ResponseEntity.ok(result.build());
    }
}
