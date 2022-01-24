package com.iomoto.demo.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document("vehicles")
@Getter
@Setter
public class VehicleModel {
    @Id
    public String id;
    @NotNull
    public String name;
    @NotNull
    @Size(max = 17)
    public String vin;
    @NotNull
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
