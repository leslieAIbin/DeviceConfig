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

    @Column(name = "username")
    private String username;

    @Column(name = "action_time")
    private String actionTime;


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "first_name")
//    private String firstName;
//    @Column(name = "last_name")
//    private String lastName;
//    @Column(name = "email", nullable = false, length = 200)
//    private String email;
}
