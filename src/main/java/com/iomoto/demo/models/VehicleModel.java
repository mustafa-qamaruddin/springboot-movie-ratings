package com.iomoto.demo.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document("vehicles")
public class VehicleModel {
    @Id
    public String id;
    @NotNull
    public String name;
    @NotNull
    public String vin;
    @NotNull
    @Size(min = 2, max = 30)
    public String licensePlateNumber;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime modified;
    // a container for all unexpected fields
    private Map<String, Object> properties;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == properties) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return properties;
    }
}
