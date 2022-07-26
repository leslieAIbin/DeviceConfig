package com.intune.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "device_state")
public class DeviceState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "cpu")
    private float cpu;

    @Column(name = "memory")
    private float memory;

    @Column(name = "action_time")
    private String actionTime;
}
