package ru.instamart.kraken.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

public enum PhoneCrypt {

    INSTANCE;

    private final int IV_SIZE = 16; // in bytes
    private static final int TAG_LENGTH_BIT = 128;
    private final String CIPHER_KEY_PHONE = "?";
    private Cipher cipher;

    public String encryptPhone(final String phone, final Key key) throws Exception {
        final byte[] iv = getRandomNonce();
        final byte[] cipherText = encrypt(phone, key, iv);

        final byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();
        return Arrays.toString(cipherTextWithIv);
    }

    private byte[] encrypt(final String phone, final Key key, final byte[] iv) throws Exception {
        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        return cipher.doFinal(phone.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] getRandomNonce() {
        final byte[] nonce = new byte[IV_SIZE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}
