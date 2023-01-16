package com.virtual.powerplant.config;

import com.virtual.powerplant.repositories.BatteryRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages={"com.virtual.powerplant.repositories"})
public class ITConfiguration extends TestConfiguration {
}