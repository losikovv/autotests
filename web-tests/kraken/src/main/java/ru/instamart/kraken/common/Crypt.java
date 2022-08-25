package ru.instamart.kraken.common;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.FileUtils;

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
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public enum Crypt {

    INSTANCE;

    private final static int SALT_LEN = 8;
    private final static int IV_BUFFER_SIZE = 16;
    private final int KEYLEN_BITS = 128;
    private final int ITERATIONS = 65536;
    private final byte[] SALT = new byte[SALT_LEN];

    private SecretKey secretKey;

    public void init() {
        try {
            var key = System.getenv("ATST_APP_SECRET_KEY");
            if (isNull(key)) {
                //For local run
                final Optional<File> keyFile = Arrays
                        .stream(FileUtils.foundFiles("../data/config", "key_"))
                        .findFirst();
                if (keyFile.isPresent()) {
                    key = keyFile.get().getName().replace("key_", "");
                } else {
                    throw new InvalidKeySpecException("Secret Key not specified");
                }
            }
            final var secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            final var pbeKeySpec = new PBEKeySpec(key.toCharArray(), SALT, ITERATIONS, KEYLEN_BITS);
            final var secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

            this.secretKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Can't init crypt algorithm = {}", e.getMessage());
        }
    }

    public final String encrypt(final String plainText) {
        try {
            final var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[IV_BUFFER_SIZE]));

            final byte[] encryptedByte = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            final var encoder = Base64.getEncoder();

            return encoder.encodeToString(encryptedByte);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Can't encrypt text='{}' with error {}", plainText, e);
        }

        return "invalid";
    }

    public final String decrypt(final String encryptedText) {
        if (isNull(encryptedText) || encryptedText.isEmpty()) {
            return null;
        }
        if (!org.apache.commons.codec.binary.Base64.isBase64(encryptedText)) {
            log.warn("Text '{}' is not in base64 and can't be decrypted", encryptedText);
            return encryptedText;
        }
        try {
            final var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final var decoder = Base64.getDecoder();
            final byte[] encryptedTextByte = decoder.decode(encryptedText.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[IV_BUFFER_SIZE]));

            return new String(cipher.doFinal(encryptedTextByte), StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Can't decrypt text='{}' with error {}", encryptedText, e);
        }

        return "wrong_key";
    }
}
