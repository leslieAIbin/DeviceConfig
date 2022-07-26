package com.intune.repository;

import com.intune.entity.DeviceConfig;
import com.intune.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface DeviceStateRepository<T>  extends CrudRepository<DeviceConfig, Long>,
        JpaRepository<DeviceConfig, Long>,
        JpaSpecificationExecutor<DeviceConfig>,
        Serializable {

    @Query(value = "SELECT * FROM device_state WHERE device_name = ?1 order by id desc limit 10", nativeQuery = true)
    List<T>  findAllByDeviceName(String device_name);

    @Query(value = "SELECT cpu FROM device_state WHERE device_name = ?1 order by id desc limit 1", nativeQuery = true)
    Integer  findCpuByDeviceName(String device_name);

    @Query(value = "SELECT memory FROM device_state WHERE device_name = ?1 order by id desc limit 1", nativeQuery = true)
    Integer  findMemoryByDeviceName(String device_name);
}
