package com.drivesoft.idmsdatafetcher.utils;

import com.drivesoft.idmsdatafetcher.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private ApplicationProperties applicationProperties;


    public String generateJwtTokenUsingUserName(String userName) {

        byte[] bytes = Base64.getDecoder().decode(applicationProperties.getJwtSecret());
        SecretKey key = new SecretKeySpec(bytes,SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(
                        new Date((new Date()).getTime() + applicationProperties.getJwtExpirationInMilliseconds()))
                .signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        String subject = Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(token)
                .getBody().getSubject();
        return subject;
    }

    public boolean validateJwtToken(String authToken)
    {
        try {
            Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                 | IllegalArgumentException e) {
            log.error("Invalid JWT Exception: {}", e.getMessage());
        }

        return false;
    }
}
