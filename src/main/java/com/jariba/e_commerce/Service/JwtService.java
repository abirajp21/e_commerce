package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

//    @Autowired
//    UserService userService;
    String JWT_SECRET= "aP2T!v%3W8d@J9e$rYq5+X^a~F&Gj$4mNnL2*Uz#o7RbMk1Q";
    String key = "";
    Map<String,  Object> claims = new HashMap<>();


    public String generateToken(String userName)
    {
        claims.put("username",userName);
        return Jwts.builder()
                .claims().add(claims).
                subject("JWT Token")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*60*3))
                .and()
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private SecretKey getKey()
    {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keygen.generateKey();
            key = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

//    public String getUserName(String token)
//    {
//       // return "abirajp04";
//        return extractClaims(token, Claims::getSubject);
//    }


    public String getUserName(String token) {
        return extractClaims(token, claims -> claims.get("username", String.class));
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver )
    {
        final Claims claims = extractAllclaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllclaims(String token)
    {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(token).getPayload();
    }


    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
