package com.example.user_service.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class HotelFeignClientInterceptor implements RequestInterceptor {

    @Autowired
     OAuth2AuthorizedClientManager manager;
    @Override
    public void apply(RequestTemplate template) {
      //attemp to authorize or reauthorize the client identified by the providedclientRegistrationId
        //id is of yml file
        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();

        template.header("Authorization","Bearer "+token);

        //this is done so that token can be taken and passed to other services
    }
}
