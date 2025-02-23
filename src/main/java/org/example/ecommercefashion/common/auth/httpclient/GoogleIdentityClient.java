package org.example.ecommercefashion.common.auth.httpclient;

import feign.QueryMap;
import org.example.ecommercefashion.common.auth.dto.GoogleLoginRequest;
import org.example.ecommercefashion.common.auth.dto.TokenGoogleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface GoogleIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenGoogleResponse exchangeToken(@QueryMap GoogleLoginRequest request);
}
