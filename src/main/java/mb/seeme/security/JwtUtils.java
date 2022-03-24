package mb.seeme.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mb.seeme.model.users.Person;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret;
    private int jwtExpirationMs;
    private String jwtCookie;

    public ResponseCookie generateJwtCookie(Person userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getName());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}