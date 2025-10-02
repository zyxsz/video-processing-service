package com.zyx.vps.application.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class RSA {

  public static RSAPublicKey readX509PublicKey(File file) throws Exception {
    String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

    String publicKeyPEM = key
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replaceAll(System.lineSeparator(), "")
        .replace("-----END PUBLIC KEY-----", "");

    byte[] encoded = Base64.decodeBase64(publicKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    return (RSAPublicKey) keyFactory.generatePublic(keySpec);
  }

  public static RSAPrivateKey readPKCS8PrivateKey(File file) throws Exception {
    String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

    String privateKeyPEM = key
        .replace("-----BEGIN RSA PRIVATE KEY-----", "")
        .replaceAll(System.lineSeparator(), "")
        .replace("-----END RSA PRIVATE KEY-----", "");

    byte[] encoded = Base64.decodeBase64(privateKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
  }

}
