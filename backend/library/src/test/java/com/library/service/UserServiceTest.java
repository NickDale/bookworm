package com.library.service;

import com.library.controller.dto.Login;
import com.library.controller.dto.PasswordChange;
import com.library.error.exception.EntityNotFoundException;
import com.library.error.exception.ForbiddenException;
import com.library.error.exception.PreConditionalException;
import com.library.error.exception.UnAuthorizedException;
import com.library.error.exception.UserBlockedException;
import com.library.model.entities.Language;
import com.library.model.entities.User;
import com.library.model.entities.UserType;
import com.library.model.entities.UserTypes;
import com.library.model.repositories.UserRepository;
import com.library.service.mail.MailService;
import com.library.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.library.error.exception.BadCredentials.BAD_CREDENTIALS;
import static com.library.util.Constant.CAN_CHANGE_ONLY_THE_OWN_PASS;
import static com.library.util.Constant.USER_IS_BLOCKED;
import static com.library.util.Constant.USER_NOT_FOUND;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final String adminMailAddress = "admin@admin.hu";

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordUtil passwordUtil;
    @Mock
    private MailService mailService;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<String> usernameCaptor;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    private List<User> users;
    private User firstActiveUser;

    @BeforeEach
    void setUp() {
        var user = new UserType(UserTypes.USER);
        var admin = new UserType(UserTypes.ADMIN);
        var librarian = new UserType(UserTypes.LIBRARIAN);

        this.firstActiveUser = User.builder()
                .userType(user)
                .email("test@test.hu")
                .username("fist_user")
                .active(true)
                .language(Language.HU)
                .firstname("test")
                .lastname("user")
                .password("123456789")
                .build();

        var fistInactiveUser = User.builder()
                .userType(user)
                .email("user@test.hu")
                .username("user")
                .active(false)
                .language(Language.HU)
                .firstname("t")
                .lastname("u")
                .build();

        var librarianUser = User.builder()
                .userType(librarian)
                .email("konyvtaros@test.hu")
                .username("gizike")
                .active(true)
                .language(Language.HU)
                .firstname("Konyves")
                .lastname("Ilona")
                .build();

        var adminUser = User.builder()
                .userType(admin)
                .language(Language.EN)
                .email(adminMailAddress)
                .username("admin")
                .password("admin")
                .active(true)
                .firstname("Nagy")
                .lastname("Adam")
                .build();

        this.users = Arrays.asList(fistInactiveUser, firstActiveUser, librarianUser, adminUser);
    }

    private List<User> filterUser(Predicate<User> pred) {
        return this.users.stream()
                .filter(pred)
                .collect(Collectors.toList());
    }

    @Test
    void changePassword_throwForbiddenException() {
        long userId = 1L;
        var passwordChange = new PasswordChange(userId, "123456789", "123456789ADSDRFDGD");
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstActiveUser));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", ""));

        var ex = assertThrows(ForbiddenException.class, () -> {
            userService.changePassword(passwordChange);
        });
        assertTrue(ex.getMessage().contains(CAN_CHANGE_ONLY_THE_OWN_PASS));
        verify(userRepository).findById(userId);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void changePassword_tryToChangeOtherUserPassword_throwForbiddenException() {
        long userId = 1L;
        var passwordChange = new PasswordChange(userId, "000", "123456789ADSDRFDGD");
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstActiveUser));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("nbalogh", ""));


        var ex = assertThrows(ForbiddenException.class, () -> {
            userService.changePassword(passwordChange);
        });
        assertTrue(ex.getMessage().contains(CAN_CHANGE_ONLY_THE_OWN_PASS));
        verify(userRepository).findById(userId);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    @DisplayName("Try to change your password - Throw BadCredentialsExp. because of wrong original password")
    void changePassword_thenThrowBadCredentialException() {
        long userId = 1L;
        var passwordChange = new PasswordChange(userId, "000", "123456789ADSDRFDGD");
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstActiveUser));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(firstActiveUser.getUsername(), ""));

        var ex = assertThrows(PreConditionalException.class, () -> {
            userService.changePassword(passwordChange);
        });
        assertTrue(ex.getMessage().contains(BAD_CREDENTIALS));
        verify(userRepository).findById(userId);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    @DisplayName("Successfully changed your password")
    void changePassword() {
        long userId = 1L;
        var passwordChange = new PasswordChange(userId, "123456789", "123456789ADSDRFDGD");

        when(userRepository.findById(userId)).thenReturn(Optional.of(firstActiveUser));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(firstActiveUser.getUsername(), ""));

        userService.changePassword(passwordChange);

        verify(userRepository).findById(userId);
        verify(passwordUtil).setCredentials(firstActiveUser, passwordChange.getNewPassword());
        verify(userRepository).save(userCaptor.capture());

        User captorUser = userCaptor.getValue();
        assertTrue(nonNull(captorUser));
        assertEquals(firstActiveUser.getUsername(), captorUser.getUsername());
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void listAllSimpleUserByFilter_withDefaultFilter_returnUserDtoList() {
        Predicate<User> userPredicate = user -> user.getUserType().getUserType().isUser();
        final String search = null;
        final int page = 0;
        final int size = 0;
        final var sorts = new String[]{"id", "asc"};
        final var expectedResponseListSize = 2;

        when(userRepository.findAll(eq(User.class), anyString(), eq(page), eq(size), eq(sorts)))
                .thenReturn(filterUser(userPredicate));

        var userDtoList = userService.listAllSimpleUserByFilter(search, page, size, sorts);

        assertEquals(expectedResponseListSize, userDtoList.size(), "size is not correct");
        userDtoList.forEach(userDto -> {
            assertTrue(nonNull(userDto), "userDto can not be null");
            assertTrue(isNotBlank(userDto.getUsername()), "username can not be null");
            assertTrue(userDto.getUserTypes().isUser(), "should be user");
        });
    }

    @Test
    void getAllLibrarian_withDefaultFilter_returnUserDtoList() {
        Predicate<User> userPredicate = user -> user.getUserType().getUserType().isLibrarian();
        final String search = null;
        final int page = 0;
        final int size = 0;
        final var sorts = new String[]{"id", "asc"};
        final var expectedResponseListSize = 1;

        when(userRepository.findAll(eq(User.class), anyString(), eq(page), eq(size), eq(sorts)))
                .thenReturn(filterUser(userPredicate));

        var userDtoList = userService.getAllLibrarian(search, page, size, sorts);

        verify(userRepository).findAll(eq(User.class), anyString(), eq(page), eq(size), eq(sorts));
        assertEquals(expectedResponseListSize, userDtoList.size(), "size is not correct");
        userDtoList.forEach(userDto -> {
            assertTrue(nonNull(userDto), "userDto can not be null");
            assertTrue(isNotBlank(userDto.getUsername()), "username can not be null");
            assertTrue(userDto.getUserTypes().isLibrarian(), "should be a librarian");
        });
    }

    @Test
    void findUserByUserName_withInvalidUsername_throwsEntityNotFoundException() {
        String username = "nope";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        var ex = assertThrows(EntityNotFoundException.class,
                () -> userService.findUserByUserName(username)
        );

        verify(userRepository).findUserByUsername(usernameCaptor.capture());
        assertEquals(USER_NOT_FOUND, ex.getMessage());
        assertEquals(username, usernameCaptor.getValue());
    }

    @Test
    void findUserByUserName_withUsername_returnUser() {
        final var username = "admin";
        final Predicate<User> adminPred = user -> user.getUsername().equals(username);
        final var adminUser = filterUser(adminPred).get(0);

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(adminUser));

        User user = userService.findUserByUserName(username);

        verify(userRepository).findUserByUsername(usernameCaptor.capture());
        assertTrue(nonNull(user));
        assertEquals(username, user.getUsername());
        assertEquals(username, usernameCaptor.getValue());
        assertTrue(user.getUserType().getUserType().isAdmin());
    }

    @Test
    void findUserByEmailIgnoreCaseOrUsername_withValidUsername_returnUser() {
        final Predicate<User> adminPred = user -> user.getEmail().equals(adminMailAddress);
        final var adminUser = filterUser(adminPred).get(0);

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(adminMailAddress))
                .thenReturn(Optional.ofNullable(adminUser));

        User user = userService.findUserByEmailIgnoreCaseOrUsername(adminMailAddress);

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(usernameCaptor.capture());
        assertTrue(nonNull(user));
        assertEquals(adminMailAddress, user.getEmail());
        assertEquals(adminMailAddress, usernameCaptor.getValue());
        assertEquals("admin", user.getUsername());
        assertTrue(user.getUserType().getUserType().isAdmin());
    }

    @Test
    void resetPassword_withInactiveUser_throwsUserBlockedException() {
        final var inactiveUser = filterUser(user -> !user.isActive()).get(0);

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(anyString())).thenReturn(Optional.of(inactiveUser));
        var ex = assertThrows(UserBlockedException.class,
                () -> userService.resetPassword(inactiveUser.getUsername())
        );

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(anyString());
        assertEquals(USER_IS_BLOCKED, ex.getMessage());
    }

    @Test
    void login_withInvalidUser_throwsUnAuthorizedException() {
        final var login = new Login("random", "random");

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(anyString()))
                .thenReturn(Optional.empty());

        var ex = assertThrows(UnAuthorizedException.class,
                () -> userService.login(login)
        );

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(usernameCaptor.capture());
        assertEquals(UNAUTHORIZED.toString(), ex.getMessage());
        assertEquals(login.getUsername(), usernameCaptor.getValue(), "username is not correct");
    }

    @Test
    void login_withInactive_throwsUserBlockedException() {
        final var login = new Login("random", "random");
        final var inactiveUser = filterUser(user -> !user.isActive()).get(0);

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(anyString()))
                .thenReturn(Optional.of(inactiveUser));

        var ex = assertThrows(UserBlockedException.class,
                () -> userService.login(login)
        );

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(usernameCaptor.capture());
        assertEquals(USER_IS_BLOCKED, ex.getMessage());
        assertEquals(login.getUsername(), usernameCaptor.getValue(), "username is not correct");
    }

    @Test
    void login_withBadCredentials_throwsUnAuthorizedException() {
        final var login = new Login("admin", "random");
        final var activeAdminUser = filterUser(user -> user.getEmail().equals(adminMailAddress)).get(0);

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(anyString()))
                .thenReturn(Optional.of(activeAdminUser));

        var ex = assertThrows(UnAuthorizedException.class,
                () -> userService.login(login)
        );

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(usernameCaptor.capture());
        assertEquals(UNAUTHORIZED.toString(), ex.getMessage());
        assertEquals(login.getUsername(), usernameCaptor.getValue(), "username is not correct");
    }

    @Test
    void login_withValidCredentials_returnLoginResponse() {
        final var login = new Login("admin", "admin");
        final var activeAdminUser = filterUser(user -> user.getEmail().equals(adminMailAddress)).get(0);

        when(userRepository.findUserByEmailIgnoreCaseOrUsername(anyString()))
                .thenReturn(Optional.of(activeAdminUser));

        var loginResponse = userService.login(login);

        verify(userRepository).findUserByEmailIgnoreCaseOrUsername(usernameCaptor.capture());

        assertTrue(nonNull(loginResponse));
        assertEquals(login.getUsername(), loginResponse.getUsername());
        assertTrue(loginResponse.getUserType().isAdmin());
        assertEquals(login.getUsername(), usernameCaptor.getValue(), "username is not correct");
    }

    @Test
    @DisplayName("Activate a user")
    void setActive() {
        final var inactiveUser = filterUser(user -> !user.isActive()).get(0);
        doReturn(inactiveUser).when(userRepository).save(any());

        userService.setActive(inactiveUser, true);
        assertTrue(inactiveUser.isActive());
    }

    @Test
    @DisplayName("Inactivate a user")
    void deactivationTest() {
        final var activeUser = filterUser(User::isActive).get(0);
        when(userRepository.save(activeUser)).thenReturn(activeUser);

        userService.setActive(activeUser, false);
        assertFalse(activeUser.isActive());
    }
}