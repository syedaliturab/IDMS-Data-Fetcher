package com.drivesoft.idmsdatafetcher.dto.response;

import lombok.Data;

@Data
public class FetchVehicleAccountFromIdmsResponse {
    private String message;
    private String startingPage;
    private String endingPage;
    private String totalPages;
}
