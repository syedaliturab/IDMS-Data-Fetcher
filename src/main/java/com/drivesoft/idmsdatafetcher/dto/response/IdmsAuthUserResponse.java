package com.drivesoft.idmsdatafetcher.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdmsAuthUserResponse {
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("Message")
    private String Message;
    @JsonProperty("Token")
    private String Token;
}
