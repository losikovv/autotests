package instamart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.function.Supplier;

public enum Crypt {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(Crypt.class);
    private final String SECRET_KEY = System.getProperty("key",
            Arrays.stream(FileUtils.foundFile(FileUtils.getResourceDir("config/"), "key_"))
            .findFirst()
            .get()
            .getName()
            .replace("key_", "")
    );

    private final static int SALT_LEN = 8;
    private final int KEYLEN_BITS = 128;
    private final int ITERATIONS = 65536;
    private final byte[] SALT = new byte[SALT_LEN];

    private SecretKey secretKey;

    private static Cipher cipher;

    public void init() {
        try {
            final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            final KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT, ITERATIONS, KEYLEN_BITS);
            final SecretKey tmp = secretKeyFactory.generateSecret(spec);
            this.secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
            log.error("Can't init crypt algorithm");
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
            final byte[] encryptedTextByte = decoder.decode(encryptedText);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));

            return new String(cipher.doFinal(encryptedTextByte), StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("Can't decrypt {}", encryptedText);
        }

        return "wrong_key";
    }
}
