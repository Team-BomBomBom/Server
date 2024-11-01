package com.bombombom.devs;

import com.bombombom.devs.core.util.RSA;
import com.bombombom.devs.dto.SignedCookies;
import jakarta.annotation.PostConstruct;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloudFrontClient {

    private PrivateKey privateKey;
    private CloudFrontUtilities cloudFrontUtilities;

    @Value("${spring.cloud.aws.cloudfront.key-pair-id}")
    private String keyPairId;

    @Value("${spring.cloud.aws.cloudfront.base-url}")
    private String baseUrl;

    @Value("${spring.cloud.aws.cloudfront.secret}")
    private String secret;

    @Value("${spring.cloud.aws.cloudfront.expire}")
    private Long expire;

    @PostConstruct
    public void init() throws Exception {
        privateKey = RSA.String2PrivateKey(secret);
        cloudFrontUtilities = CloudFrontUtilities.create();
    }

    private String getResourceUrl(String objectId) {
        return baseUrl + "/outputs/" + objectId + "/Default/HLS/*";
    }

    public SignedCookies getSignedCookies(String objectId) {
        Instant expirationDate = Instant.now().plus(expire, ChronoUnit.SECONDS);
        CannedSignerRequest cannedRequest = CannedSignerRequest.builder()
            .resourceUrl(getResourceUrl(objectId))
            .privateKey(privateKey)
            .keyPairId(keyPairId)
            .expirationDate(expirationDate)
            .build();

        return SignedCookies.fromPolicy(cloudFrontUtilities.getCookiesForCannedPolicy(
            cannedRequest));
    }

}
