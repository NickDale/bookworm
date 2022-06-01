package com.library.util;

import com.library.model.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Slf4j
@Component
public class PasswordUtil {

    private static final String HASH_ALGORITHM = "SHA1PRNG";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d][^@$!%*#?&]{6,}$";
    private final static int ITERATION_NUMBER = 842;

    public boolean isSaltAndPasswordEquals(String password, String digest, String salt) {
        byte[] proposedDigest = getHash(ITERATION_NUMBER, password, base64ToByte(salt));
        return Arrays.equals(proposedDigest, base64ToByte(digest));
    }

    public void setCredentials(@NonNull final User user, @NonNull final String password) {
        var random = getSecureRandom();
        var bSalt = new byte[8];
        random.nextBytes(bSalt);
        user.setPassword(byteToBase64(getHash(ITERATION_NUMBER, password, bSalt)));
        user.setSalt(byteToBase64(bSalt));
    }

    public byte[] getHash(int iterationNb, String password, byte[] salt) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            log.error("Something went wrong ", ex);
            throw new RuntimeException("Something went wrong with createCredentials");
        }

        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

    private byte[] base64ToByte(String data) {
        return Base64.getDecoder().decode(data);
    }

    private String byteToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public String generateRandomPassword() {
        var random = getSecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    private SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            log.error("Something went wrong ", ex);
            throw new RuntimeException("Something went wrong with createCredentials");
        }
    }

}

