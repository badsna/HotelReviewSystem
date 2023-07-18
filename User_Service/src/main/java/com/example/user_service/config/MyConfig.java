package com.example.user_service.config;

import com.example.user_service.config.interceptor.RestTemplateInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        //  return new RestTemplate();
        //for resttemplate interceptors

        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>();
        //passing second method as parameter
        interceptorList.add(new RestTemplateInterceptor(manager(
                clientRegistrationRepository,
                oAuth2AuthorizedClientRepository
        )));
        restTemplate.setInterceptors(interceptorList);
        return restTemplate;


    }

    //declare bean of OAuth2AuthorizedClient manager
    /*
    ClientRegistrationRepository ====> provides the capability to store a sub set copy of the primary client registration information externally from the Authorization Server
    OAuth2AuthorizedClientRepository =====> responsible for  the persistence of Authorized Client between request
     */
    @Bean
    public OAuth2AuthorizedClientManager manager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
    ) {
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, auth2AuthorizedClientRepository);

        //set provider
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;
    }
}

