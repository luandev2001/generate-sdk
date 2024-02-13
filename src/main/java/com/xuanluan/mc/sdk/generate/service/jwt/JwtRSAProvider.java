package com.xuanluan.mc.sdk.generate.service.jwt;

import com.xuanluan.mc.sdk.exception.MessageSourceException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtRSAProvider {
    public static Claims decode(String token, PublicKey publicKey) {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException exception) {
            throw new MessageSourceException("token.expired", HttpStatus.REQUEST_TIMEOUT, "");
        } catch (IllegalArgumentException exception) {
            throw new MessageSourceException("token.invalid", HttpStatus.BAD_REQUEST, "");
        }
    }

    public static JwtBuilder encode(Claims claims, long expirationTime, PrivateKey privateKey) {
        Date currentDate = new Date();
        Date expirationDay = new Date(currentDate.getTime() + expirationTime * 1000); // expirationTime * 1000 = milliseconds
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDay)
                .signWith(SignatureAlgorithm.RS256, privateKey);
    }
}
