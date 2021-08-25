package com.cardinity.assessment.utils;

import com.cardinity.assessment.helper.ApplicationContextHolder;
import com.cardinity.assessment.props.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@UtilityClass
public class JWTUtils {

    private final static AppProperties prop;
    static {
        prop = ApplicationContextHolder.getContext().getBean(AppProperties.class);
    }


    /* PUBLIC METHODS */
    public static String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public static String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public static String trimToken(String barerToken){
        String tokenPrefix = String.format("%s ", prop.getTokenPrefix());
        return StringUtils.replace(barerToken, tokenPrefix, "");
    }

    public static boolean isTokenValid(String token, String username){
        boolean isValidUsername = StringUtils.equals(extractUserName(token), username);
        return ( isValidUsername && !isTokenExpired(token));
    }

    public static boolean isTokenInvalidOrExpired(String token, String username){
        return !isTokenValid(token, username);
    }

    /* PRIVATES */
    private static String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiryMilli()))
                .signWith(SignatureAlgorithm.HS256, prop.getJwtSecret())
                .compact();
    }

    private static int getExpiryMilli(){
        return DateTimeUtils.convertToMilli(prop.getTokenExpiryMinute(), Calendar.MINUTE);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(prop.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
