package com.bombombom.devs.dto;


import lombok.Builder;
import software.amazon.awssdk.services.cloudfront.cookie.CookiesForCannedPolicy;

@Builder
public record SignedCookies(
    String keyPairId,
    String expire,
    String signature,
    String resourceUrl
) {


    public static SignedCookies fromPolicy(CookiesForCannedPolicy policy) {
        return SignedCookies.builder()
            .signature(policy.signatureHeaderValue())
            .expire(policy.expiresHeaderValue())
            .keyPairId(policy.keyPairIdHeaderValue())
            .resourceUrl(policy.resourceUrl())
            .build();
    }
}
