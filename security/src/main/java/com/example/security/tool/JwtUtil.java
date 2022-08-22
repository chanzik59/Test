package com.example.security.tool;

import com.sun.istack.internal.NotNull;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chenzhiqin
 * @date 2022/8/22 11:36
 * @info X
 */
public class JwtUtil {
    private static final Long JWT_TTL = 60 * 60 * 1000L;
    private static final String JWT_KEY = "jwt";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createJwt(String subject) {
        return getJwtBuilder(subject, null, getUUID()).compact();
    }

    public static String createJwt(String subject, Long ttl) {
        return getJwtBuilder(subject, ttl, getUUID()).compact();
    }

    public static String createJwt(String subject, Long ttl, String uuid) {
        return getJwtBuilder(subject, ttl, uuid).compact();
    }

    private static JwtBuilder getJwtBuilder(@NotNull String subject, Long ttl, @NotNull String uuid) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKey secretKey = getSecretKey();
        long millis = System.currentTimeMillis();
        Date now = new Date(millis);
        long expire = millis + Optional.ofNullable(ttl).orElse(JWT_TTL);
        Date expireDate = new Date(expire);
        return Jwts.builder().setId(uuid).setSubject(subject).setIssuer("jw").setIssuedAt(now).signWith(hs256, secretKey).setExpiration(expireDate);

    }

    private static SecretKey getSecretKey() {
        byte[] decode = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }

    public static Claims parseJwt(String jwt) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(jwt).getBody();
    }

}
