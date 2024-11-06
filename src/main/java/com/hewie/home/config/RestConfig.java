package com.hewie.home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpsClientRequestFactory factory = new HttpsClientRequestFactory ();
        factory.setConnectTimeout(15 * 1000);
        factory.setReadTimeout(5 * 1000);
//        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("xxx", 8080)));

        RestTemplate restTemplate = new RestTemplate(factory);
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(restTemplate.getMessageConverters());
        messageConverters.add(messageConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
