package com.isimm.suivi_note.services;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.utils.KeyUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class JWTService {

    private static final String TOKE_TYPE = "token_type";
    private final  PrivateKey privateKey;
    private final  PublicKey publicKey;
    @Value("${app.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${app.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;    

    public JWTService() throws Exception{
        this.privateKey=KeyUtils.loadPrivateKey("keys/private_key.pem");
        this.publicKey=KeyUtils.loadPublicKey("keys/public_key.pem");
    }

    public String generateAccessToken(final String userCin){
        final Map <String,Object> claims=Map.of(TOKE_TYPE,"ACCESS_TOKEN");
        return buildToken(userCin,claims,this.accessTokenExpiration);
    }

    public String generateRefreshToken(final String userCin){
        final Map <String,Object> claims=Map.of(TOKE_TYPE,"REFRESH_TOKEN");
        return buildToken(userCin,claims,this.refreshTokenExpiration);
    }

    private String buildToken(final String userCin,final Map<String,Object>claims, final long expiration) {
        
        return Jwts.builder()
                    .claims(claims)
                    .subject(userCin)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+expiration))
                    .signWith(this.privateKey)
                    .compact(); 
    }

    public boolean isTokenValid(final String expectedUserCin,final String token){
        final String userCin = extractUserCin(token);
        return userCin.equals(expectedUserCin) && !isTokenExpired(token);
    }

    public String extractUserCin(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(final String token){
        try{

            return Jwts.parser()
                       .verifyWith(publicKey)
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();

        }catch(final JwtException ex){
            throw new JwtException("invalid token !",ex);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String refreshAccessToken(String refreshToken){
        final Claims claims = extractClaims(refreshToken);
        if(!claims.get(JWTService.TOKE_TYPE).equals("REFRESH_TOKEN")){
            throw new RuntimeException("invalid token type");
        }
        if(isTokenExpired(refreshToken)){
            throw new RuntimeException("refresh token expired");
        }
        final String userCin = claims.getSubject();
        return generateAccessToken(userCin);
    }

    public String getTokenFromCookie(HttpServletRequest request,String tokenType) {
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if (tokenType.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }
    return null;
}

}
