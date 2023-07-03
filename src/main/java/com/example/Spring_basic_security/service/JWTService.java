package com.example.Spring_basic_security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;


import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);

    }



    public List<String> extractAuthorities(String token) {
        final Claims claims = extractAllClaims(token);
        List<String> authorities = claims.get("authorities", List.class);

        if (authorities == null) {
            return Collections.emptyList();
        }

        return authorities.stream()
                .map(String::new)
                .collect(Collectors.toList());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
//        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
//        List<String> authorities1 = new ArrayList<>(grantedAuthorities);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token) );
    }

//    private boolean authoritiesMatch(List<String> authorities1, List<String> authorities2) {
//        // Compare the authorities of the user in UserDetails with the authorities extracted from the token
//        Set<String> authorities1Set = new HashSet<>(authorities1);
//        Set<String> authorities2Set = new HashSet<>(authorities2);
//
//        return authorities1Set.equals(authorities2Set);
//    }

    public String generateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String,Object> claims,String userName){
        return Jwts.builder().setClaims(claims).setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*30))
                        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }

    private Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
