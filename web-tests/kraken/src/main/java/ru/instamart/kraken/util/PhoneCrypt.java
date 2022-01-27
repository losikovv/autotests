package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public enum PhoneCrypt {

    INSTANCE;

    //AES-256-CBC
    private static final String ENCRYPT_ALGO = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM="AES";
    private static final int IV_SIZE = 16; // in bytes
    //"Qqh79BpFGPpEWpdsNsZDwHgo65Xa5LHy53ahcN9caHM="
    private static final byte[] CIPHER_KEY_PHONE = new byte[]{66, -88, 123, -12, 26, 69, 24, -6, 68, 90, -105, 108, 54, -58, 67, -64, 120, 40, -21, -107, -38, -28, -79, -14, -25, 118, -95, 112, -33, 92, 104, 115};
    private static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(CIPHER_KEY_PHONE, ALGORITHM);
    private static final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * def encrypt(plain, key: ENV['CIPHER_KEY'])
     *       raise MissingKeyError unless key
     *
     *       cipher = OpenSSL::Cipher.new(CIPHER_256).encrypt
     *       iv = cipher.random_iv
     *       cipher.key = key.unpack('m')[0]
     *       cipher.iv = iv
     *       [iv + cipher.update(plain) + cipher.final].pack('m0')
     *     end
     */
    public String encryptPhone(final String phone) {
        final byte[] iv = getRandomNonce();
        final var ivParameterSpec = new IvParameterSpec(iv);
        String result = "error";
        try {
            final var cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, ivParameterSpec);

            try(final var outputStream = new ByteArrayOutputStream()) {
                outputStream.write(iv);
                outputStream.write(cipher.update(phone.getBytes(StandardCharsets.UTF_8)));
                outputStream.write(cipher.doFinal());
                result = encoder.encodeToString(outputStream.toByteArray());
            } catch (Exception e) {
                log.error("FATAL: Can't concatenate byte");
            }

            return result;
        } catch (Exception e) {
            log.error("FATAL: Can't encrypt phone={}", phone);
        }
        return result;
    }

    private byte[] getRandomNonce() {
        final byte[] nonce = new byte[IV_SIZE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}
