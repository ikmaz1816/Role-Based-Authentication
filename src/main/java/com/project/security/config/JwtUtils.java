package com.project.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;

    public String extractName(String jwtToken)
    {
        return getClaimInfo(Claims::getSubject,jwtToken);
    }
    public Date getExpiration(String jwtToken)
    {
        return getClaimInfo(Claims::getExpiration,jwtToken);
    }
    public <T> T getClaimInfo(Function<Claims,T> claimsResolver,String jwtToken)
    {
        Claims claims=getClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    public Claims getClaims(String jwtToken)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(jwtToken)
                .getBody();
    }
    public Key getSigningKey()
    {
        byte[] keys= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keys);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails)
    {
        String username=extractName(jwtToken);
        return username.equals(userDetails.getUsername())
                && !isExpired(jwtToken);

    }

    private boolean isExpired(String jwtToken) {
        Date date=getExpiration(jwtToken);
        return date.before(new Date());
    }

    public String generateToken(UserDetails userDetails, HashMap<String,Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenWithoutClaims(UserDetails userDetails)
    {
        return generateToken(userDetails, new HashMap<>());
    }

}
