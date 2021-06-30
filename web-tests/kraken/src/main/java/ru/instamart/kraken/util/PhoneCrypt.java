package ru.instamart.kraken.util;

import lombok.SneakyThrows;
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
    private static final byte[] CIPHER_KEY_PHONE = "Qqh79BpFGPpEWpdsNsZDwHgo65Xa5LHy53ahcN9caHM=".getBytes(StandardCharsets.UTF_8);

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
        final Base64.Encoder encoder = Base64.getEncoder();
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] encryptedTextByte = decoder.decode(CIPHER_KEY_PHONE);
        final byte[] iv = getRandomNonce();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final SecretKeySpec secretKey = new SecretKeySpec(encryptedTextByte, ALGORITHM);
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try {
            final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            outputStream.write(iv);
            outputStream.write(cipher.update(phone.getBytes(StandardCharsets.UTF_8)));
            outputStream.write(cipher.doFinal());

            return encoder.encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("FATAL: Can't encrypt phone={}", phone);
        }
        return "";
    }

    private byte[] getRandomNonce() {
        final byte[] nonce = new byte[IV_SIZE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}
