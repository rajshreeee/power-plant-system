package com.virtual.powerplant.utils;

public class BatteryConstants {

    private BatteryConstants() {}

    public static final String BASE_API_URL = "/batteries";
    public static final String AVERAGE_WATT_CAPACITY = "avgWattCapacity";
    public static final String TOTAL_WATT_CAPACITY = "totalWattCapacity";

    // Fields
    public static final String NAME = "name";
    public static final String WATT = "watt";

    public static final String POST_CODE = "postCode";

    //QueryParams
    public static final String INCLUDE_META_DATA = "includeMetaData";
    public static final String MIN_POST_CODE = "minPostCode";
    public static final String MAX_POST_CODE = "maxPostCode";

    // Exception Messages
    public static final String NAME_NOT_BLANK_MSG = "Name must not be blank.";
    public static final String WATT_CAPACITY_POSITIVE_MSG = "Watt capacity must be greater than 0.";
    public static final String POST_CODE_POSITIVE_MSG = "PostCode must be greater than 0.";
    public static final String POST_CODE_RANGE_VALIDATION_MSG = "Minimum PostCode should be lesser than Maximum PostCode.";
}
