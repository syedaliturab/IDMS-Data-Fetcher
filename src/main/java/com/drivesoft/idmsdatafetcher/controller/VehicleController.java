package com.drivesoft.idmsdatafetcher.controller;

import com.drivesoft.idmsdatafetcher.dto.response.FetchVehicleAccountFromIdmsResponse;
import com.drivesoft.idmsdatafetcher.dto.response.VehicleAccountsResponse;
import com.drivesoft.idmsdatafetcher.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/vechile")
@Tag(name = "Vechile APIS", description = "Operations related to vechile account information")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    @Operation(
            summary = "For fetching idms account",
            description = "Retrieve a idms account related to vehicle and save them local db.")
    @PostMapping("/fetchIdmsAccount")
    public ResponseEntity<FetchVehicleAccountFromIdmsResponse> fetchAccountFromIdms(@RequestParam(defaultValue = "1") Integer pageNumber) {
        log.info("Request to fetch account from idms  {}", pageNumber);
       return ResponseEntity.ok().body( vehicleService.fetchVechileAccountFromIdms(1));
    }
    @Operation(
            summary = "For getting account",
            description = "Retrieve a idms accounts for vehicles from local db."
            )
    @GetMapping("/accounts")
    public ResponseEntity<VehicleAccountsResponse> getAccounts(@RequestParam(defaultValue = "0") Integer pageNumber) {
        log.info("request to get vechile accounts: {}", pageNumber);
        return ResponseEntity.ok().body(vehicleService.getVechileAccounts(pageNumber));
    }
}
