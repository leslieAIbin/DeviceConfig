package com.intune.repository;

import com.intune.entity.DeviceConfig;
import com.intune.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface DeviceConfigRepository
   extends CrudRepository<DeviceConfig, Long>,
        JpaRepository<DeviceConfig, Long>,
        JpaSpecificationExecutor<DeviceConfig>,
        Serializable {
    DeviceConfig findByDeviceName(String deviceName);
    DeviceConfig findByDeviceId(String deviceId);


}
