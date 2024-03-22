package org.shoppingMall.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${jwtpassword.source")
    private String secretKey;
    private String key;

    @PostConstruct
    public void setUp(){
        key= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    private long tokenValidMillisecond= 1000L * 60 * 60;

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Token");
    }

    public String createToken(String email, List<String> roles){
        Claims claims= Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date now= new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validateToken(String jwtToken){
        try{
            Claims claims= Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
            Date now= new Date();
            return !claims.getExpiration().before(now);
        } catch(Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String jwtToken) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody().getSubject();
    }

}
