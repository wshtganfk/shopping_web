package com.bfs.shopping_web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("$security.jwt.token.key")
    private String key;


    public Optional<AuthUserDetail> resolveToken(HttpServletRequest request){
        String prefixedToken = request.getHeader("Authorization"); // extract token value by key "Authorization"
        System.out.println(prefixedToken);
        if(StringUtils.isEmpty(prefixedToken)) return Optional.empty();
        else {
            String token = prefixedToken.substring(7); // remove the prefix "Bearer "

            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody(); // decode

            String username = claims.getSubject();
            System.out.println(username);
            List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");
            permissions.forEach(e -> System.out.println(e));

            List<GrantedAuthority> authorities = permissions.stream()
                    .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                    .collect(Collectors.toList());

            //return a userDetail object with the permissions the user has
            return Optional.of(AuthUserDetail.builder()
                    .username(username)
                    .authorities(authorities)
                    .build());
        }

    }
    public String createToken(UserDetails userDetails){
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername()); // user identifier
        claims.put("permissions", userDetails.getAuthorities()); // user permission
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key) // algorithm and key to sign the token
                .compact();
    }

}
