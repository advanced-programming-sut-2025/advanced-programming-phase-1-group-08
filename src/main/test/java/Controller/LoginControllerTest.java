package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @Test
    void testLoginWithValidCredentials() {
        String str = loginController.LoginRes("kir", "kk" ).toString();
        assertNotEquals("kir", str);
    }
}
