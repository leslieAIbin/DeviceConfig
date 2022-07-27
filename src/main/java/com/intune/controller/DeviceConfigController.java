package com.intune.controller;

import com.intune.entity.DeviceConfig;
import com.intune.entity.DeviceState;
import com.intune.entity.Result;
import com.intune.exception.DeviceNotFound;
import com.intune.exception.InternalServerError;
import com.intune.repository.DeviceConfigRepository;
import com.intune.repository.DeviceStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/intune")
public class DeviceConfigController {
    @Autowired
    DeviceConfigRepository deviceConfigRepository;
    @Autowired
    DeviceStateRepository deviceStateRepository;
    Date dateTime = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Get all device configs
     * @return
     */
    @GetMapping("/deviceConfig")
    public Result getAllDeviceConfig() {
        try {
            List<DeviceConfig> deviceConfigs = new ArrayList<DeviceConfig>();
            deviceConfigRepository.findAll().forEach(deviceConfigs::add);
            return new Result().success(deviceConfigs);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }


    /**
     * get device config by deviceId
     * @param deviceId
     * @return
     */
    @GetMapping("/deviceConfig/{deviceId}")
    public Result
    getDeviceConfigByID(@PathVariable("deviceId") String deviceId) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceId(deviceId);
        if (!deviceConfigData.equals(null)) {
            return new Result().success(deviceConfigData);
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }


    /**
     * get device config upload by deviceId
     * @param deviceId
     * @return
     */
    @GetMapping("/deviceConfig/{deviceId}/upload")
    public Result
    getDeviceConfigUploadByID(@PathVariable("deviceId") String deviceId) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceId(deviceId);
        if (!deviceConfigData.equals(null)) {
            return new Result().success(deviceConfigData.isLogUpload());
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }

    }

    /**
     * create device config
     * @param deviceConfig
     * @return
     */
    @PostMapping("/deviceConfig")
    public Result
    createDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        try {
            DeviceConfig newDeviceConfig = new DeviceConfig();
            newDeviceConfig.setDeviceName(deviceConfig.getDeviceName());
            newDeviceConfig.setLogUpload(true);
            newDeviceConfig.setUsername(deviceConfig.getUsername());
            newDeviceConfig.setActionTime(ft.format(dateTime));
            deviceConfigRepository.save(newDeviceConfig);
            return new Result().success(newDeviceConfig);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Delete device config
     * @param deviceId
     * @return
     */
    @DeleteMapping("/deviceConfig/{deviceId}")
    public Result
    deleteDeviceConfig(@RequestParam("deviceId") String deviceId) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceId(deviceId);
        if (deviceConfigData != null) {
            deviceConfigRepository.deleteById(deviceConfigData.getId());
            return new Result().success();
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }


    /**
     * Update device config by device id
     * @param deviceConfig
     * @return
     */
    @PutMapping("/deviceConfig")
    public Result
    updateDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        if(deviceConfig.getDeviceName() == null) {
            throw new DeviceNotFound("Invalid Device Id");
        }
        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceId(deviceConfig.getDeviceId());
        if (deviceConfigData != null) {
            DeviceConfig _deviceConfig = deviceConfigData;
            _deviceConfig.setLogUpload(deviceConfig.isLogUpload());
            _deviceConfig.setCpuCheck(deviceConfig.isCpuCheck());
            _deviceConfig.setMemoryCheck(deviceConfig.isMemoryCheck());
            _deviceConfig.setActionTime(ft.format(dateTime));
            deviceConfigRepository.save(_deviceConfig);
            return new Result().success(_deviceConfig);
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }

    /**
     * get device config by deviceId
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceIsUpload/{deviceName}")
    public Result
    getDeviceConfigByName(@PathVariable("deviceName") String deviceName) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceName);
        if (!deviceConfigData.equals(null)) {
            return new Result().success(deviceConfigData.isLogUpload());
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }

    /**
     * get device state by device name
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceState/{deviceName}")
    public Result
    getDeviceStateByName(@PathVariable("deviceName") String deviceName) {

        try {
            List<DeviceState> deviceStates ;
            deviceStates = deviceStateRepository.findAllByDeviceName(deviceName);
            if(deviceStates.isEmpty()){
                throw new DeviceNotFound("Invalid Device Name");
            }
            return new Result().success(deviceStates);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * get device state by device name
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceCpu/{deviceName}")
    public Result
    getDeviceCpuByName(@PathVariable("deviceName") String deviceName) {

        try {
            Integer cpu = deviceStateRepository.findCpuByDeviceName(deviceName);
            return new Result().success(cpu);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }
    /**
     * get device state by device name
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceMemory/{deviceName}")
    public Result
    getDeviceMemoryByName(@PathVariable("deviceName") String deviceName) {

        try {
            Integer memory = deviceStateRepository.findMemoryByDeviceName(deviceName);
            return new Result().success(memory);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }



    /**
     * create device State
     * @param deviceState
     * @return
     */
    @PostMapping("/deviceState")
    public Result
    createDeviceState(@RequestBody DeviceState deviceState) {
        try {
            DeviceState newDeviceState = new DeviceState();
            newDeviceState.setDeviceName(deviceState.getDeviceName());
            newDeviceState.setCpu(deviceState.getCpu());
            newDeviceState.setMemory(deviceState.getMemory());
            newDeviceState.setActionTime(ft.format(dateTime));
            deviceStateRepository.save(newDeviceState);
            return new Result().success(newDeviceState);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }


    @GetMapping("/helloworld")
    public Result
    helloworld() {
        try {
            return new Result().success("Hello World 0726");
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * get device config by deviceId
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceCpuCheck/{deviceName}")
    public Result
    getDeviceCpuCheckByName(@PathVariable("deviceName") String deviceName) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceName);
        if (!deviceConfigData.equals(null)) {
            return new Result().success(deviceConfigData.isCpuCheck());
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }
    /**
     * get device config by deviceId
     * @param deviceName
     * @return
     */
    @GetMapping("/deviceMemoryCheck/{deviceName}")
    public Result
    getDeviceMemoryCheckByName(@PathVariable("deviceName") String deviceName) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceName);
        if (!deviceConfigData.equals(null)) {
            return new Result().success(deviceConfigData.isMemoryCheck());
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }


}
