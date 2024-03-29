package com.intune.entity;
import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "device_config")
public class DeviceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "log_upload")
    private boolean logUpload;

    @Column(name = "cpu_check")
    private boolean cpuCheck;

    @Column(name = "memory_check")
    private boolean memoryCheck;

    @Column(name = "username")
    private String username;

    @Column(name = "action_time")
    private Date actionTime;

    @Column(name = "device_id")
    private String deviceId;
}
