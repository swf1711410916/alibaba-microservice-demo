package com.demo.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.*;

public class JwtGenerateUtil {

    /**
     * RSA 私钥（PKCS8）
     */
    private static final String PRIVATE_KEY = """
         -----BEGIN PRIVATE KEY-----
         MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRgToO5oMVsqaX
         YoZZMMhxMguDESTTIRaP5WuPDVg8RXSnkX8Uq4unmRGAPEVGMCY517zDTVbj4FNk
         nA/NVNHPh8yVdNa41aayXIcjpbM4U/Ayyunz2GItazJdno4kKy9vz1ZBk2AV3c6E
         SHri/x//lERptKjd2UHx2H+f38KaajpRDRBppHs0L/ZiYiXXuOltSQaEJyW43TBf
         oMkVxE817N5K4vMh57GYLvCp5+M2C0P7M15F+GYybcl2S6uKFfweqFccAuWuP5Oi
         vkfYirXoOJX1NUtfw+paIb1fiv2BIETbA5+u3sN9YURJC+uNxhsb4wzHg11Ldf3P
         2fzOyHd1AgMBAAECggEBAL96do/Lyp74Kx5NZDD8TxZ2AOOHtbppkW9u5YFXVv+v
         E1Fbv2Qq7Xb+hWpIq5/cvfM75EAz3WhkTJO1txOZpDEjEq6+nMlOLA70VmcUONsQ
         jIY/wtelOwX75Yp/MbaI6dAtFc7VXYpesTNh8z6oopw67NQX1Ab/XDAoCW0bW4c4
         SqgcF23oUyS7qZrmrc5miSjFw5Bnn/qA19bh/xiY996r3rAizoykzXSuFEp4z69r
         Aa7HRFaKL8F5D0ULLRmFUUTkN7qs0PY4BE3zAMoV2u99EpysaHaRsLiXInxaYhNm
         IW9tvAtrCPqyAC/zm44VXQVsauffEV8H6OXMmjpOjPkCgYEA/Kz4voLsfJNTEKA3
         qhpoTg8OMym8eDOz88dbPm2xssIxpw6EQBaZ/fnVC/aqBcVNKljLoJv0tt4WNM2n
         YvPamPu531cKImNYNPaUlnRM1rwd5CPETAIww6Rvmgt4pIReomhKkZa0m+Sn7CIe
         +dZF7NgCVPD9HXuJ+qQ0Aop5tTsCgYEA1ELaTrrgtlFFMESNyhfaNlo1ViBSAc0y
         7+Mvkt5Vkre0fcgEv03cJsCAeWne4WWnLbkJMlxCwiCvEs7FmA2pNPOR6t8DxikG
         QEJlAN+eamCHPlZqif2ZRkabwU2+Fuebq4CQvt4r3M/9hLH3TDtA6csbgBibqjcx
         FoYsIK+C+w8CgYEA1NRViRL6Y0wssVpiyLMJnIZt7YaAK7dRIkuPfZwkC3ArJo3B
         s8HV3+mqHDOertGr17mHezch/0494ig/j8fbHvUZcRf9f02ypJn2OmyntFAr1ViT
         JXrK3GALaPO0oM6mb6Hs/Yi1hIIrN2jeMo7bw02HWUjLyyOd6zKYZHODZLUCgYBY
         jvfkJyYACxJR3BhIPAFn8eUU5PEyHTnmSiQcI7USPKEKZXxNkJ+Mb35qqZg69B12
         /x9uKn4T3m0M7MYV9YUmOYUkuB0r/QvFna3kWmgOu+1RPd6/adYcMvNjCBuKFOGn
         AUHi0HD+dKyyQ0oJPSONJ5d2r02rC9HoMtyQWonMUwKBgCgQ/1lDep7x9ropOAUO
         p7JC2PBtbP7EVAlfFfIkPSoVeoenkALlIdBnQvkazwRqjsUESNKjwFWqsXPoLtLl
         g+Qmkw49UfytNbW6EaBMAc1ZgcR2BpKpi6ayRO5QghTOeGrSJWfT72vA4Wi2OCZp
         OiraaX5p6fhLBZVyD6TEN6sk
         -----END PRIVATE KEY-----     
         """;

    /**
     * 生成 JWT Token
     */
    public static String generateToken(
            String subject,
            Map<String, Object> claims,
            long expireSeconds
    ) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expireSeconds)))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 解析 RSA 私钥
     */
    private static PrivateKey getPrivateKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(
                    PRIVATE_KEY.replaceAll("-----\\w+ PRIVATE KEY-----", "")
                            .replaceAll("\\s+", "")
            );

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("加载 RSA 私钥失败", e);
        }
    }
    /*public static void main(String[] args) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("username", "zhangsan");
        claims.put("roles", List.of("ADMIN"));
        claims.put("permissions", List.of(
                "order:create",
                "order:query"
        ));
        String token = generateToken("10001", claims, 3600);
        System.out.println(token);
    }*/
}
