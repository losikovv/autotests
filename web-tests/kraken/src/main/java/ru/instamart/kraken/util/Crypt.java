package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public enum Crypt {

    INSTANCE;

    private final static int SALT_LEN = 8;
    private final int KEYLEN_BITS = 128;
    private final int ITERATIONS = 65536;
    private final byte[] SALT = new byte[SALT_LEN];

    private SecretKey secretKey;

    private static Cipher cipher;

    public void init() {
        try {
            String key = System.getProperty("key", "LNnLjMGmXrao25HKlp46QGdyNhUs3hJA");
            if (isNull(key)) {
                //For local run
                final Optional<File> keyFile = Arrays.stream(FileUtils.foundFile("../data/config/", "key_")).findFirst();
                if (keyFile.isPresent()) {
                    key = keyFile.get().getName().replace("key_", "");
                } else {
                    throw new InvalidKeySpecException("Secret Key not specified");
                }
            }
            final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            final KeySpec spec = new PBEKeySpec(key.toCharArray(), SALT, ITERATIONS, KEYLEN_BITS);
            final SecretKey tmp = secretKeyFactory.generateSecret(spec);
            this.secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
            log.error("Can't init crypt algorithm = {}", e.getMessage());
        }
    }

    public final String encrypt(final String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));

            final byte[] encryptedByte = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            final Base64.Encoder encoder = Base64.getEncoder();

            return encoder.encodeToString(encryptedByte);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            log.error("Can't encrypt text {}", plainText);
        }

        return "invalid";
    }

    public final String decrypt(final String encryptedText) {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final byte[] encryptedTextByte = decoder.decode(encryptedText.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));

            return new String(cipher.doFinal(encryptedTextByte), StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("Can't decrypt {}", encryptedText);
        }

        return "wrong_key";
    }
}
