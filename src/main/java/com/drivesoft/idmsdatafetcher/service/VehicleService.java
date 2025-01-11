package com.drivesoft.idmsdatafetcher.service;

import com.drivesoft.idmsdatafetcher.dto.response.FetchVehicleAccountFromIdmsResponse;
import com.drivesoft.idmsdatafetcher.dto.response.VehicleAccountsResponse;

public interface VehicleService {
     FetchVehicleAccountFromIdmsResponse fetchVechileAccountFromIdms(Integer pageNumber);
     VehicleAccountsResponse getVechileAccounts(Integer pageNumber);
}
