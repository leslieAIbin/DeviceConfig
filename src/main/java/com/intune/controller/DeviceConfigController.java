package com.intune.controller;

import com.intune.entity.DeviceConfig;
import com.intune.entity.Result;
import com.intune.exception.DeviceNotFound;
import com.intune.exception.InternalServerError;
import com.intune.repository.DeviceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.SimpleFormatter;

@RestController
@RequestMapping("/api/v1/intune")
public class DeviceConfigController {
    @Autowired
    DeviceConfigRepository deviceConfigRepository;

    Date dateTime = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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


    @GetMapping("/deviceConfig/{id}")
    public ResponseEntity<DeviceConfig>
    getDeviceConfigByID(@PathVariable("id") Long id) {

        Optional<DeviceConfig> deviceConfigData = deviceConfigRepository.findById(id);
        if (deviceConfigData.isPresent()) {
            return new ResponseEntity<>
                    (deviceConfigData.get(), HttpStatus.OK);
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }

    }

    @GetMapping("/deviceConfig/deviceName")
    public Result
    getDeviceConfigByName(@RequestParam("deviceName") String deviceName) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceName);
        return new Result().success(deviceConfigData);
    }

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

    @DeleteMapping("/deviceConfig/deviceName")
    public Result
    deleteDeviceConfig(@RequestParam("deviceName") String deviceName) {

        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceName);
        if (deviceConfigData != null) {
            deviceConfigRepository.deleteById(deviceConfigData.getId());
            return new Result().success();
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }

    @PutMapping("/deviceConfig/deviceName")
    public Result
    updateDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        if(deviceConfig.getDeviceName() == null) {
            throw new DeviceNotFound("Invalid Device Id");
        }
        DeviceConfig deviceConfigData = deviceConfigRepository.findByDeviceName(deviceConfig.getDeviceName());
        if (deviceConfigData != null) {
            DeviceConfig _deviceConfig = deviceConfigData;
            _deviceConfig.setLogUpload(deviceConfig.isLogUpload());
            _deviceConfig.setActionTime(ft.format(dateTime));
            deviceConfigRepository.save(_deviceConfig);
            return new Result().success(_deviceConfig);
        } else {
            throw new DeviceNotFound("Invalid Device Id");
        }
    }
}
