package cn.itcast.travel.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/15 18:07
 * @description：Token工具类
 * @modified By：
 * @version: $
 */
public class JWTUtils {
    private static final long EXPIRE_TIME = 24*60*60*1000;
    private static final String TOKEN_SECRET = "Hzhang12580";

    /**
     * 生成token
     * @param username
     * @param password
     * @return
     */
    public static String sign(String username, String password){
        // 过期时间
        Date date = new Date((System.currentTimeMillis() + EXPIRE_TIME));
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        // 构建JWT对象
        String signData = JWT.create().withHeader(header)
                .withClaim("username", username)
                .withClaim("password", password)
                .withExpiresAt(date)
                .sign(algorithm);
        return signData;
    }

    /**
     * 校验Token
     * @param token
     * @param username
     * @param password
     * @return
     */
    public static boolean verify(String token, String username, String password){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 构建JWT验证对象
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            // 获得解码后的JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            // 提取用户名密码
            String JWTUsername = decodedJWT.getClaim("username").asString();
            String JWTPassword = decodedJWT.getClaim("password").asString();
            if(!username.equals(JWTUsername) || !password.equals(JWTPassword)){
                return false;
            }
            if(System.currentTimeMillis() > decodedJWT.getExpiresAt().getTime()){
                return false;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return true;
    }
}
