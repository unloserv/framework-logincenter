package com.canghuang.logincenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.canghuang.logincenter.entity.User;

import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

/**
 * @author cs
 * @date 2018/9/11
 * @description
 */
public class AccessToken {

    private final static String JWTSignature_Base64 = Base64.getEncoder().encodeToString("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9".getBytes());
    private final static String JWTIssuer = "canghuang";
    private final static String JWTSubject = "logincenter";
    private final static String JWTAudience = "general-app";

    /**
     * 生成登录令牌
     * @param redisKey 当前用户在redis中的key
     * @param expTime 当前用户在redis中的过期时间
     * @param user 当前用户 涉及属性id、name、userface、lastLoginTime、lastLoginIp、registerTime
     * @return
     */
    public static String build(String redisKey, Date expTime, User user) {
        return JWT.create()
                .withKeyId(redisKey)
                /*
                    The iss (issuer) claim identifies the principal that issued the JWT.
                    The processing of this claim is generally application specific.
                    The iss value is a case-sensitive string containing a StringOrURI value.
                    Use of this claim is OPTIONAL.
                 */
                .withIssuer(JWTIssuer)
                /*
                    The sub (subject) claim identifies the principal that is the subject of the JWT.
                    The claims in a JWT are normally statements about the subject.
                    The subject value MUST either be scoped to be locally unique in the context of the issuer or be globally unique.
                    The processing of this claim is generally application specific.
                    The sub value is a case-sensitive string containing a StringOrURI value.
                    Use of this claim is OPTIONAL.
                 */
                .withSubject(JWTSubject)
                /*
                    The aud (audience) claim identifies the recipients that the JWT is intended for.
                    Each principal intended to process the JWT MUST identify itself with a value in the audience claim.
                    If the principal processing the claim does not identify itself with a value in the aud claim when this claim is present,
                    then the JWT MUST be rejected. In the general case, the aud value is an array of case-sensitive strings,
                    each containing a StringOrURI value. In the special case when the JWT has one audience,
                    the aud value MAY be a single case-sensitive string containing a StringOrURI value.
                    The interpretation of audience values is generally application specific.
                    Use of this claim is OPTIONAL.
                */
                .withAudience(JWTAudience)
                /*
                    The exp (expiration time) claim identifies the expiration time on or after which the JWT MUST NOT be accepted for processing.
                    The processing of the exp claim requires that the current date/time MUST be before the expiration date/time listed in the exp claim.
                    Implementers MAY provide for some small leeway, usually no more than a few minutes, to account for clock skew.
                    Its value MUST be a number containing a NumericDate value.
                    Use of this claim is OPTIONAL.
                */
                .withExpiresAt(expTime)
                /*
                    The nbf (not before) claim identifies the time before which the JWT MUST NOT be accepted for processing.
                    The processing of the nbf claim requires that the current date/time MUST be after or equal to the not-before date/time
                    listed in the nbf claim.
                    Implementers MAY provide for some small leeway, usually no more than a few minutes, to account for clock skew.
                    Its value MUST be a number containing a NumericDate value.
                    Use of this claim is OPTIONAL.
                */
                .withNotBefore(new Date())
                /*
                    The iat (issued at) claim identifies the time at which the JWT was issued.
                    This claim can be used to determine the age of the JWT.
                    Its value MUST be a number containing a NumericDate value.
                    Use of this claim is OPTIONAL.
                 */
                .withIssuedAt(new Date())
                /*
                    The jti (JWT ID) claim provides a unique identifier for the JWT.
                    The identifier value MUST be assigned in a manner that ensures that there is a negligible probability that the same value
                    will be accidentally assigned to a different data object;
                    if the application uses multiple issuers, collisions MUST be prevented among values produced by different issuers as well.
                    The jti claim can be used to prevent the JWT from being replayed. The jti value is a case-sensitive string.
                    Use of this claim is OPTIONAL.
                 */


                // 自定义

                .withClaim("uid", user.getId())
                .withClaim("uname", user.getName())
                .withClaim("uface", user.getUserface())
                .withClaim("ullt", user.getLastLoginTime() == null ? null : user.getLastLoginTime().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .withClaim("ulli", user.getLastLoginIp())
                .withClaim("urt", user.getRegisterTime() == null ? null : user.getRegisterTime().toInstant(ZoneOffset.of("+8")).toEpochMilli())

                /*
                    .withClaim("uid", user.getId())
                    .withClaim("uname", user.getName())
                    .withClaim("uface", user.getUserface())
                    .withClaim("ullt", user.getLastLoginTime().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                    .withClaim("ulli", user.getLastLoginIp())
                    .withClaim("urt", u)
                */
                .sign(Algorithm.HMAC256(JWTSignature_Base64));
    }

    /**
     * 验证登录令牌并返回解析后的令牌
     * @param accessToken
     * @return
     */
    public static DecodedJWT verify(final String accessToken) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(JWTSignature_Base64);
        //Reusable verifier instance
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWTIssuer).build();
        DecodedJWT jwt = verifier.verify(accessToken);
        return jwt;
    }

    /**
     * 根据account计算tokenId的值
     * @param account
     * @return tokenId
     */
    public static String tokenId(final String account) {
        return EncryptUtil.md5Encode(account);
    }
}
