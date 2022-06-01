package com.library.service;

import com.library.controller.dto.AddUser;
import com.library.controller.dto.Login;
import com.library.controller.dto.LoginResponse;
import com.library.controller.dto.ModifyUser;
import com.library.controller.dto.PasswordChange;
import com.library.controller.dto.RegistrationRequest;
import com.library.controller.dto.UserDto;
import com.library.controller.dto.UserUpdateRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.library.error.exception.BadCredentials.BAD_CREDENTIALS;
import static com.library.model.entities.UserTypes.LIBRARIAN;
import static com.library.model.entities.UserTypes.USER;
import static com.library.util.Constant.ADMIN_CAN_ONLY_MODIFY_LIBRARIANS;
import static com.library.util.Constant.CAN_CHANGE_ONLY_THE_OWN_PASS;
import static com.library.util.Constant.EMAIL_ALREADY_EXIST;
import static com.library.util.Constant.USER_ALREADY;
import static com.library.util.Constant.USER_NOT_FOUND;
import static com.library.util.Constant.USER_NOT_FOUND_BY_ID;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final MailService mailService;

    public List<UserDto> listAllSimpleUserByFilter(final String search, final int page, final int size, final String[] sorts) {
        return listUsersByFilter(UserTypes.USER, search, page, size, sorts);
    }

    public UserDto findUserDtoById(final long id) {
        return toDto(findUserById(id));
    }

    public User findUserById(final long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_BY_ID, id));
    }

    public UserDto saveUser(@NonNull final AddUser addUser) {
        return saveUser(addUser, USER);
    }

    public void deactivateUserById(final long userId) {
        setActive(userId, FALSE);
    }

    public void setActive(final long userId) {
        setActive(userId, TRUE);
    }

    public void setActive(final long userId, final boolean activation) {
        var user = findUserById(userId);
        if (user.isActive() == activation) {
            throw new PreConditionalException(USER_ALREADY, activation ? "active" : "inactive");
        }
        setActive(user, activation);
    }

    @Transactional
    public void setActive(@NonNull final User user, final boolean activation) {
        user.setDeactivationDate(activation ? null : LocalDateTime.now());
        user.setActive(activation);
        userRepository.save(user);

        mailService.sendActivationMail(user, activation);
    }

    public void activateUserByUserId(Long userId) {
        setActive(userId);
    }

    @Transactional
    public void changePassword(@NonNull final PasswordChange passwordChange) {
        var user = findUserById(passwordChange.getUserId());
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw new ForbiddenException(CAN_CHANGE_ONLY_THE_OWN_PASS);
        }
        if (isPasswordNotCorrect(user.getSalt(), user.getPassword(), passwordChange.getOldPassword())) {
            throw new PreConditionalException(BAD_CREDENTIALS);
        }
        passwordUtil.setCredentials(user, passwordChange.getNewPassword());
        userRepository.save(user);
    }

    boolean isPasswordNotCorrect(final String salt, final String savedPassword, final String inputPassword) {
        return !(isNotBlank(salt)
                ? passwordUtil.isSaltAndPasswordEquals(inputPassword, savedPassword, salt)
                : inputPassword.equals(savedPassword));
    }

    public List<UserType> listVisibleUserTypes() {
        return userRepository.findAll(UserType.class);
    }

    public LoginResponse login(@NonNull final Login login) {
        var user = userRepository.findUserByEmailIgnoreCaseOrUsername(login.getUsername())
                .orElseThrow(UnAuthorizedException::new);
        if (user.isActive() || (nonNull(user.getDeactivationDate()) && LocalDateTime.now().isBefore(user.getDeactivationDate()))) {
            if (isPasswordNotCorrect(user.getSalt(), user.getPassword(), login.getPassword())) {
                throw new UnAuthorizedException();
            }
            return new LoginResponse(user);
        }
        throw new UserBlockedException();
    }

    public User findUserByEmailIgnoreCaseOrUsername(final String username) {
        return userRepository.findUserByEmailIgnoreCaseOrUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public List<UserDto> getAllLibrarian(String search, int page, int size, String[] sorts) {
        return listUsersByFilter(LIBRARIAN, search, page, size, sorts);
    }

    private List<UserDto> listUsersByFilter(@NonNull UserTypes userType,
                                            String search, int page, int size, String[] sorts) {
        if (isNotBlank(search)) {
            search += ",";
        }
        search += "userTypeId:" + userType.getId();

        return userRepository.findAll(User.class, search, page, size, sorts)
                .stream()
                .map(UserDto::new)
                .collect(toList());
    }

    @Transactional
    public UserDto modifyLibrarian(@NonNull final ModifyUser modifyUser) {
        var originalUser = findUserById(modifyUser.getUserId());
        if (!LIBRARIAN.equals(originalUser.getUserType().getUserType())) {
            throw new ForbiddenException(ADMIN_CAN_ONLY_MODIFY_LIBRARIANS);
        }
        originalUser.setModifications(modifyUser);
        return toDto(userRepository.save(originalUser));
    }

    public UserDto upsertLibrarian(@NonNull AddUser addUser) {
        return saveUser(addUser, LIBRARIAN);
    }

    @Transactional
    public UserDto saveUser(AddUser addUser, UserTypes userType) {
        var user = new User(addUser);
        if (userRepository.existsUserByEmailIgnoreCase(user.getEmail())) {
            throw new PreConditionalException(EMAIL_ALREADY_EXIST);
        }
        user.setUserTypeId(userType.getId());
        user.setUsername(generateUsername(addUser));
        user.setQrCode(user.getUsername());//TODO change it
        var generatePassword = passwordUtil.generateRandomPassword();
        passwordUtil.setCredentials(user, generatePassword);

        //TODO send mail if the user with they role
        return toDto(userRepository.save(user));
    }

    public UserDto toDto(@NonNull final User user) {
        return new UserDto(user);
    }

    private String generateUsername(@NonNull final AddUser addUser) {
        return generateUsername(addUser.getFirstname(), addUser.getLastname());
    }

    private String generateUsername(final String firstName, final String lastname) {
        var username = firstName.trim().substring(0, 3)
                + lastname.trim().substring(0, 3);
        username = username.toLowerCase();
        int count = userRepository.countByUsernameLike(username + "%");
        return count == 0
                ? username
                : username + (count + 1);
    }

    public User findUserLoggedInUser() {
        return findUserByUserName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public User findUserByUserName(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public UserDto getDetailsOfLoggedInUser() {
        return new UserDto(findUserLoggedInUser());
    }

    public void resetPassword(@NonNull final String username) {
        var user = findUserByEmailIgnoreCaseOrUsername(username);
        if (!user.isActive()) {
            throw new UserBlockedException();
        }
        var generatePassword = passwordUtil.generateRandomPassword();
        passwordUtil.setCredentials(user, generatePassword);

        userRepository.save(user);
        mailService.sendForgottenPasswordMail(user, generatePassword);
    }

    @Transactional
    public void uploadProfileImg(byte[] fileBites) {
        var user = findUserLoggedInUser();
        user.setProfileImage(fileBites);
    }

    @Transactional
    public void updateProfile(UserUpdateRequest updateRequest) {
        var user = findUserLoggedInUser();

        if (!user.getEmail().equalsIgnoreCase(updateRequest.getEmail()) &&
                userRepository.existsUserByEmailIgnoreCase(updateRequest.getEmail())) {
            throw new PreConditionalException(EMAIL_ALREADY_EXIST);
        }
        user.setEmail(updateRequest.getEmail());
        user.setFirstname(updateRequest.getFirstname());
        user.setLastname(updateRequest.getLastname());
        user.setPhoneNumber(updateRequest.getPhone());
        userRepository.save(user);
    }

    public boolean checkEmailAddress(final String email) {
        var user = findUserLoggedInUser();
        return user.getEmail().equalsIgnoreCase(email) ||
                !userRepository.existsUserByEmailIgnoreCase(email);
    }

    @Transactional
    public void registerNewUser(final RegistrationRequest registrationRequest) {
        boolean emailAlreadyExists = userRepository.existsUserByEmailIgnoreCase(registrationRequest.getEmail());
        if (emailAlreadyExists) {
            throw new PreConditionalException(EMAIL_ALREADY_EXIST);
        }
        var user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setFirstname(registrationRequest.getFirstname());
        user.setLastname(registrationRequest.getLastname());
        user.setUserTypeId(USER.getId());
        user.setLanguage(Language.HU);
        user.setUsername(generateUsername(registrationRequest.getFirstname(), registrationRequest.getLastname()));
        user.setActive(true); //TODO: create activation link

        var generatePassword = passwordUtil.generateRandomPassword();
        passwordUtil.setCredentials(user, generatePassword);

        userRepository.save(user);
        mailService.sendRegistrationMail(user, generatePassword);
    }
}
