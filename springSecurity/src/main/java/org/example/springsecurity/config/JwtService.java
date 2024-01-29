package org.example.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "392e806100c1afac25bd26a1fb2fc8b418bd60931acaede31943b310fcc93193290c1dfd4d8940e30cbe1030b6859a3dcb2976445c2c5832b75a1cb2f0e73bb7588c11491d91bbbad20250a0cd000f020c87d8d937d5a5714519c8169215953abfa76c73d1d6c70e6f3f20dc5ebddf73fe125491cbca1e9afb971a25d1baa8d2473e1feb812daef21583d086f9da93b0d6a67e622fae9d75d3a468be87f5bc43dbaf16a58dced0815a6deceae4e8c96b28f92deb01f9ad004a595ce1a52e2f5bb55e34381e265e93ba3399c7cea14ad4778f276e15ffc9a95c5d60f7a1a4ee463fc3a4f7e0d9367b380ef1c327325718ae2544729fcab31630124857df9ba52e";
    public String extractUsername(String JwtToken) {
        return extractClaim(JwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String JwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(JwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String JwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(JwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
