package group4.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public class jwtService {

    @Value("${4dc7bb66f7beb450bf2f3588932fd988589bd5f1f40f2ce79bf799fb50ebcb3de31321bcfed27f1322751bb4eaa165399dca507ab79bcee4ab6c537eadf644ba3323862903d1edddd41206a528e0d5d92889fcb4c33b2148d360cfb87eba23b43941e5d45ad7bbebb5ce05213afe38e47405f24e18bdd11d070248945ad4a7924bfd2663318d98008f4f807454fdd7b5d519540f791121b7d3d37e067275eb66fb680e14a0797c8ded1176cd77c088b221b0f1ddfa662dd889ea0c672c67a2729db927191a96afdf6decc18e9cd84ab66b265afe655209941af16e4e715fdc9e0e278e912d6b32f36dcb47e1bcbe9418c45d46efa2faae929ddba09c62eee4a4}")
    private String secretKey;

    public String extraxtUsername(String token) throws JwtException {
        return null;

    }

    public <T>


    private Claims extractAllClaims(String token) throws JwtException{
        return Jwts.parserBuilder().setSigningKey(getSignInKey).build().parseClaimsJws(token).getBody();

    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
