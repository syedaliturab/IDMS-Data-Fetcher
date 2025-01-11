package com.drivesoft.idmsdatafetcher.service.Impl;

import com.drivesoft.idmsdatafetcher.config.ApplicationProperties;
import com.drivesoft.idmsdatafetcher.dto.response.IdmsAuthUserResponse;
import com.drivesoft.idmsdatafetcher.exception.CommonException;
import com.drivesoft.idmsdatafetcher.service.IDMSService;
import com.drivesoft.idmsdatafetcher.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@Service
public class IDMSServiceImpl implements IDMSService {
    @Autowired
    ApplicationProperties applicationProperties;
    @Override
    public IdmsAuthUserResponse authenticateIdmsUser() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
            String url=applicationProperties.getIdmsBaseUrl()+applicationProperties.getIdmsUserAuthApi();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("username", applicationProperties.getIdmsUsername())
                    .queryParam("password", applicationProperties.getIdmsPassword())
                    .queryParam("InstitutionID", applicationProperties.getIdmsInstitutionId());
            String uri = URLDecoder.decode(uriBuilder.toUriString(), StandardCharsets.UTF_8);
            ResponseEntity<IdmsAuthUserResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, IdmsAuthUserResponse.class );
            return response.getBody();

        } catch (Exception e) {
            log.error("Error while authorizing idms user: {} ", e.getMessage());
            throw new CommonException(AppConstant.EROOR_IN_GETTING_IDMS_TOKEN,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @Override
    public String getIdmsAccounts(Integer pageNumber ,String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
            String url=applicationProperties.getIdmsBaseUrl()+applicationProperties.getIdmsGetAccountListApi();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("Token", token)
                    .queryParam("AccountStatus", applicationProperties.getIdmsAccountStatus())
                    .queryParam("LayoutID", applicationProperties.getIdmsLayoutId())
                    .queryParam("PageNumber", pageNumber);
            ResponseEntity<String> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, httpEntity,String.class );
            return response.getBody();

        } catch (Exception e) {
            log.error(AppConstant.FETCHING_IDMS_ACCOUNT_FAILDED+": {} ", e.getMessage());
            throw new CommonException(AppConstant.FETCHING_IDMS_ACCOUNT_FAILDED,HttpStatus.FAILED_DEPENDENCY);

        }
    }
}
