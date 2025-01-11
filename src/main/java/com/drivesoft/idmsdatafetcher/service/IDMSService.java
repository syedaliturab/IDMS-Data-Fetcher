package com.drivesoft.idmsdatafetcher.service;

import com.drivesoft.idmsdatafetcher.dto.response.IdmsAuthUserResponse;

public interface IDMSService {
public IdmsAuthUserResponse authenticateIdmsUser();
public String getIdmsAccounts(Integer pageNumber,String token);
}
