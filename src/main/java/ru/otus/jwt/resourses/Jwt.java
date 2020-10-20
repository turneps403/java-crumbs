package ru.otus.jwt.resourses;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
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

    @RequestMapping("/recognize")
    public Map recognize(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        httpResponse.setHeader(RESPONSE_HEADER, DEFAULT_VALUE);
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
                    httpResponse.setHeader(RESPONSE_HEADER, claims.get(DESIRABLE_KEY).toString());
                } catch (Exception e) {
                    logger.log(Level.WARNING, "invalid/expired token", e);
                }
            } else {
                logger.log(Level.WARNING, "Authorization token must be Bearer [token]");
            }
        } else {
            logger.log(Level.WARNING, "Authorization token wasn't provided");
        }
        return Collections.singletonMap("status", "OK");
    }

}
