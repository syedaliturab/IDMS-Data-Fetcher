package com.drivesoft.idmsdatafetcher.dto.response;

import com.drivesoft.idmsdatafetcher.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class VehicleAccountsResponse {
   String message;
   List<Account> data;
   Integer pageNo ;
   Integer pageSize ;
   Integer totalPages ;
}
