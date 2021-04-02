package com.Bootcamp.Project.Application.springSecurity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public class TokenProvider{
    String secret;
    Integer duration;
    public String createToken( String username){
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.duration);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("user", username)
                .signWith(SignatureAlgorithm.HS512,secret)
                .setExpiration(validity)
                .compact();
        return token;
    }
    public TokenProvider() {
    }

    public TokenProvider(String secret, Integer duration) {
        this.secret = secret;
        this.duration = duration;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        System.out.println(expiration.before(new Date()));
        return expiration.before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
