package com.drivesoft.idmsdatafetcher.service.Impl;

import com.drivesoft.idmsdatafetcher.config.ApplicationProperties;
import com.drivesoft.idmsdatafetcher.dto.response.FetchVehicleAccountFromIdmsResponse;
import com.drivesoft.idmsdatafetcher.dto.response.IdmsAuthUserResponse;
import com.drivesoft.idmsdatafetcher.dto.response.VehicleAccountsResponse;
import com.drivesoft.idmsdatafetcher.entity.Account;
import com.drivesoft.idmsdatafetcher.exception.CommonException;
import com.drivesoft.idmsdatafetcher.service.AccountService;
import com.drivesoft.idmsdatafetcher.service.IDMSService;
import com.drivesoft.idmsdatafetcher.service.VehicleService;
import com.drivesoft.idmsdatafetcher.utils.AppConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    IDMSService idmsService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public FetchVehicleAccountFromIdmsResponse fetchVechileAccountFromIdms(Integer pageNumber) {

        FetchVehicleAccountFromIdmsResponse fetchVehicleAccountFromIdmsResponse = new FetchVehicleAccountFromIdmsResponse();
            IdmsAuthUserResponse idmsAuthUserResponse = idmsService.authenticateIdmsUser();
            if (idmsAuthUserResponse == null || ObjectUtils.isEmpty(idmsAuthUserResponse.getToken())) {
                String errorMessage = (idmsAuthUserResponse != null)
                        ? String.format(AppConstant.EROOR_IN_GETTING_IDMS_TOKEN+": %s", idmsAuthUserResponse.getMessage())
                        : AppConstant.EMPTY_RESPONSE_FROM_IDMS;
                log.error(errorMessage);
                throw new CommonException(AppConstant.IDMS_LOGIN_FAILED, HttpStatus.FAILED_DEPENDENCY);
            }
            String accountList = idmsService.getIdmsAccounts(1, idmsAuthUserResponse.getToken());
            List<Account> list = getVechileAccountsFromIdmsAccounts(accountList);
            accountService.addAccounts(list);
            generateFetchVechileAccountIdmsResponse(accountList, fetchVehicleAccountFromIdmsResponse);

        return fetchVehicleAccountFromIdmsResponse;
    }

    private void generateFetchVechileAccountIdmsResponse(String accountList, FetchVehicleAccountFromIdmsResponse fetchVehicleAccountFromIdmsResponse)  {
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(accountList);

        fetchVehicleAccountFromIdmsResponse.setStartingPage(rootNode.path("BeginningPage").asText());
        fetchVehicleAccountFromIdmsResponse.setEndingPage(rootNode.path("EndingPage").asText());
        fetchVehicleAccountFromIdmsResponse.setTotalPages(rootNode.path("TotalPages").asText());
        fetchVehicleAccountFromIdmsResponse.setMessage("success");
        } catch (Exception e) {
            log.error("Error in generating generateFetchVechileAccountIdmsResponse :{}",e.getMessage());
            throw new CommonException(AppConstant.FETCHING_IDMS_ACCOUNT_FAILDED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public VehicleAccountsResponse getVechileAccounts(Integer pageNumber) {
        VehicleAccountsResponse vehicleAccountsResponse =new VehicleAccountsResponse();
        Pageable pageable = PageRequest.of(pageNumber, applicationProperties.getVechileAccountPageSize());
        Page<Account> accountPage=accountService.getAccounts(pageable);
        vehicleAccountsResponse.setData(accountPage.getContent());
        vehicleAccountsResponse.setPageNo(accountPage.getNumber());
        vehicleAccountsResponse.setPageSize(accountPage.getSize());
        vehicleAccountsResponse.setTotalPages(accountPage.getTotalPages());
        vehicleAccountsResponse.setMessage("success");
        return vehicleAccountsResponse;
    }

    private List<Account> getVechileAccountsFromIdmsAccounts(String idmsAccounts) {
        List<Account> vechileAccountList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(idmsAccounts);
            JsonNode dataNode = rootNode.path("Data");
            for (JsonNode rowNode : dataNode) {
                JsonNode row = rowNode.path("Row");
                Account vechileAccount = new Account();
                vechileAccount.setContractSalesPrice(row.path("ContractSalesPrice").asText());
                vechileAccount.setAcctType(row.path("AcctType").asText());
                vechileAccount.setSalesGroupPerson1ID(row.path("SalesGroupPerson1ID").asText());
                vechileAccount.setContractDate(row.path("ContractDate").asText());
                vechileAccount.setCollateralStockNumber(row.path("CollateralStockNumber").asText());
                vechileAccount.setCollateralYearModel(row.path("CollateralYearModel").asText());
                vechileAccount.setCollateralMake(row.path("CollateralMake").asText());
                vechileAccount.setCollateralModel(row.path("CollateralModel").asText());
                vechileAccount.setBorrower1FirstName(row.path("Borrower1FirstName").asText());
                vechileAccount.setBorrower1LastName(row.path("Borrower1LastName").asText());
                vechileAccount.setAcctID(row.path("AcctID").asText());
                vechileAccountList.add(vechileAccount);
            }
        } catch (Exception e) {
            log.error("Error while fetching account details : {}", e.getMessage());
            throw new CommonException(AppConstant.FETCHING_IDMS_ACCOUNT_FAILDED,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return vechileAccountList;
    }
}
