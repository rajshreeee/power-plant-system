package com.virtual.powerplant.repositories;

import com.virtual.powerplant.models.Battery;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BatteryRepository extends MongoRepository<Battery, String> {
    List<Battery> findByPostCodeBetween(Range<Long> postCodeRange, Sort sort);

    List<Battery> findByPostCodeIsGreaterThanEqual(Long postCode, Sort sort);

    List<Battery> findByPostCodeIsLessThanEqual(Long postCode, Sort sort);
}
