package com.library.util;

import com.library.model.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PasswordUtilTest {

    @InjectMocks
    private PasswordUtil passwordUtil;

    @Test
    @DisplayName("Password - salt validation test")
    void isSaltAndPasswordEquals() {
        final String inputPassword = "alma";
        final var user = new User();
        user.setPassword("bo/tZDKFDAcHhs0mcGsPbjCcXpk=");
        user.setSalt("GY0UKj/V7BE=");

        boolean saltAndPasswordEquals = passwordUtil.isSaltAndPasswordEquals(inputPassword, user.getPassword(), user.getSalt());
        assertTrue(saltAndPasswordEquals);
    }

    @Test
    @DisplayName("Password and salt creation test")
    void setCredentials() {
        var user = new User();
        String givenPassword = "alma";

        passwordUtil.setCredentials(user, givenPassword);
        boolean saltAndPasswordEquals = passwordUtil.isSaltAndPasswordEquals(givenPassword, user.getPassword(), user.getSalt());

        assertTrue(isNotBlank(user.getPassword()));
        assertTrue(isNotBlank(user.getSalt()));
        assertTrue(saltAndPasswordEquals);
    }

}