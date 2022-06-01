package com.library;

import com.library.controller.docs.LoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("NewClassNamingConvention")
public class LibraryApplicationIT {

    @MockBean
    private LoginController loginController;

    @Test
    @DisplayName("Application starting test")
    void contextLoads() {
    }

    @Test
    @DisplayName("Spring Dependency Injection test")
    public void loginControllerLoad() {
        assertThat(loginController).isNotNull();
    }
}
