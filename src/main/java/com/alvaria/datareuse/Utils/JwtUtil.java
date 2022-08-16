package com.alvaria.datareuse.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET = "CCMetric";

    public static String generateToken(Map<String, String> payload) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);
        JWTCreator.Builder builder = JWT.create();
        payload.forEach(builder::withClaim);
        return builder.withAudience("admin")
                .withIssuedAt(new Date())
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    public static Map<String, Claim> getPayloadByToken(String token) {
        return verify(token).getClaims();
    }

}
