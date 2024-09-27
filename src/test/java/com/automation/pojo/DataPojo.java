package com.automation.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class DataPojo {
    int year;
    double price;
    @JsonProperty("CPU model")
    String CPUModel;
    @JsonProperty("Hard disk size")
    String HardDiskSize;
}
