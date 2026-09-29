package com.zee.course.auth.payment.authpaymentservice.util;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.json.JSONObject;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Component
public class PaymentUtil {

    public String getUsernameFromAccessToken(JwtAuthenticationToken token) {
        return getValueFromAccessToken(token.getToken().getTokenValue(), "username");
    }

    public String getUsernameFromAccessToken(String token) {
        return getValueFromAccessToken(token, "username");
    }

    public JSONObject jwtJsonObject(String token){
        try {
            if(org.springframework.util.StringUtils.hasText(token)){
                JWT jwt = JWTParser.parse(token);
                return new JSONObject(jwt.getJWTClaimsSet().getClaims());
            }
        }catch (Exception _){
            return null;
        }

        return null;
    }

    public String getValueFromAccessToken(String token, String key) {
        JSONObject jwtObject = jwtJsonObject(token);
        if(jwtObject != null){
            return jwtObject.getString(key);
        }
        return null;
    }
}
