package com.gustavo.helpdesk_api.infra.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PkceUtil {

   public static String generateCodeVerifier() {
      SecureRandom sr = new SecureRandom();
      byte[] code = new byte[32];
      sr.nextBytes(code);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
   }

   public static String generateCodeChallenge(String verifier) {
      try {
         byte[] bytes = verifier.getBytes(StandardCharsets.US_ASCII);
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         byte[] digest = md.digest(bytes);
         return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("Erro ao gerar Code Challenge", e);
      }
   }
}