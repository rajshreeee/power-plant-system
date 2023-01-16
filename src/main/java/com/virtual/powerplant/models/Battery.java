package com.virtual.powerplant.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("batteries")
@Getter
@Setter
public class Battery{

    @Id
    private String id;

    private String name;

    private Long postCode;

    private Integer wattCapacity;

    public Battery(String name, Long postCode, Integer wattCapacity) {
        this.name = name;
        this.postCode = postCode;
        this.wattCapacity = wattCapacity;
    }
}
