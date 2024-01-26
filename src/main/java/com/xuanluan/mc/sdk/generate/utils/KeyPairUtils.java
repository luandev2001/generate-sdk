package com.xuanluan.mc.sdk.generate.utils;

import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class KeyPairUtils {
    private static final Set<String> algorithms = Collections.singleton("RSA");

    private KeyPairUtils() {

    }

    public static String encodeBase64(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    public static byte[] decodeBase64(String value) {
        return Base64.getDecoder().decode(value);
    }

    public static PrivateKey privateKey(String value) {
        return processPrivateKey(Base64.getDecoder().decode(value), "RSA");
    }

    public static PrivateKey privateKey(String value, String type) {
        return processPrivateKey(Base64.getDecoder().decode(value), type);
    }

    public static PrivateKey privateKey(byte[] value, String type) {
        return processPrivateKey(value, type);
    }

    private static PrivateKey processPrivateKey(byte[] value, String type) {
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(value);
        try {
            return keyFactory(type).generatePrivate(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey publicKey(String value) {
        return processPublicKey(Base64.getDecoder().decode(value), "RSA");
    }

    public static PublicKey publicKey(String value, String type) {
        return processPublicKey(Base64.getDecoder().decode(value), type);
    }


    public static PublicKey publicKey(byte[] value, String type) {
        return processPublicKey(value, type);
    }


    private static PublicKey processPublicKey(byte[] value, String type) {
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(value);
        try {
            return keyFactory(type).generatePublic(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static KeyFactory keyFactory(String type) {
        AssertUtils.notBlank(type, "type");
        AssertUtils.isTrue(algorithms.contains(type), "Invalid algorithm, " + type + " is not supported");
        try {
            return KeyFactory.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair(String type) {
        AssertUtils.notBlank(type, "type");
        AssertUtils.isTrue(algorithms.contains(type), "Invalid algorithm, " + type + " is not supported");
        try {
            return KeyPairGenerator.getInstance(type).generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
