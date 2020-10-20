package ru.otus.jwt.resourses;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/jwt", produces = MediaType.APPLICATION_JSON_VALUE)
public class Jwt {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Value("${jwt.body.desirableKey}")
    String DESIRABLE_KEY;

    @Value("${jwt.body.desirableKey.defaultValue}")
    String DEFAULT_VALUE;

    @Value("${jwt.reponseHeader}")
    String RESPONSE_HEADER;

    @Value("${jwt.secretKey}")
    String SECRET_KEY;

    @Value("${jwt.ttl}")
    String JWT_TTL;

    @RequestMapping("/auth")
    public Map authentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        httpResponse.setHeader(RESPONSE_HEADER, DEFAULT_VALUE);
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
                    DecodedJWT jwt = verifier.verify(token);
                    httpResponse.setHeader(RESPONSE_HEADER, jwt.getClaim(DESIRABLE_KEY).asString());
                } catch (Exception e) {
                    logger.log(Level.WARNING, "invalid/expired token");
                }
            } else {
                logger.log(Level.WARNING, "Authorization token must be Bearer [token]");
            }
        } else {
            logger.log(Level.WARNING, "Authorization token wasn't provided");
        }
        return Collections.singletonMap("status", "OK");
    }

    @RequestMapping("/login")
    public Map login(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Authorization", "Bearer " + createJWToken("100500"));
        return Collections.singletonMap("status", "OK");
    }

    private String createJWToken(String keyValue) {
        long timestamp = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
            .withIssuer("auth0")
            .withIssuedAt(new Date(timestamp))
            .withExpiresAt(new Date(timestamp + Integer.parseInt(JWT_TTL)))
            .withClaim(DESIRABLE_KEY, keyValue)
            .sign(algorithm);
        return token;
    }

}
