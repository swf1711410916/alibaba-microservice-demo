package com.demo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwtUtil {

    private static final String PUBLIC_KEY = """
        -----BEGIN PUBLIC KEY-----
        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0YE6DuaDFbKml2KGWTDI
        cTILgxEk0yEWj+Vrjw1YPEV0p5F/FKuLp5kRgDxFRjAmOde8w01W4+BTZJwPzVTR
        z4fMlXTWuNWmslyHI6WzOFPwMsrp89hiLWsyXZ6OJCsvb89WQZNgFd3OhEh64v8f
        /5REabSo3dlB8dh/n9/Cmmo6UQ0QaaR7NC/2YmIl17jpbUkGhCcluN0wX6DJFcRP
        NezeSuLzIeexmC7wqefjNgtD+zNeRfhmMm3JdkurihX8HqhXHALlrj+Tor5H2Iq1
        6DiV9TVLX8PqWiG9X4r9gSBE2wOfrt7DfWFESQvrjcYbG+MMx4NdS3X9z9n8zsh3
        dQIDAQAB
        -----END PUBLIC KEY-----
        """;

    public static Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static PublicKey getPublicKey() {
        try {
            byte[] keyBytes = Base64.getDecoder()
                    .decode(PUBLIC_KEY.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s+", ""));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJwZXJtaXNzaW9ucyI6WyJvcmRlcjpjcmVhdGUiLCJvcmRlcjpxdWVyeSJdLCJyb2xlcyI6WyJBRE1JTiJdLCJ1c2VybmFtZSI6InpoYW5nc2FuIiwic3ViIjoiMTAwMDEiLCJpYXQiOjE3NjY0OTUzNzksImV4cCI6MTc2NjQ5ODk3OX0.A3zqrLU2k836iQqGkhmCvq2_qdgvVB0irhkndcrFITmQHV9NWHkA4zPaLOKMSUolcO553T6lyctGZQZaVW19M7HuQbUDcgLd36QndSXZI2MMVMjmO_tfpnHVVfDSanOa6hCPae7yw08pLMnT6xKsZZxRqHdkUH1hfNq4z6cHpKZMbpqj7-htacPoTJIIPxIh6LXxOhaMikAlCJStVsmh8z8jrYOXsdzGNY57NbCPk2p0-5645K4xZkniKXHpGFzfm85P1ZIDUaHJz6HFkiKB4NTKfhcKmdJ-i1QZlMS-ENO6zbFG_xMT-rwn0D2BDTCEOjmxqdaLuDD-g4sIj4wJyw";
        System.out.println(parse(token));
    }*/
}
