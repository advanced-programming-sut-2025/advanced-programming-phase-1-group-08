package Controller;

import model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @Test
    void testLoginWithValidCredentials() throws IOException {
        Result result = loginController.LoginRes("Erfan4", "Erfan!2321");
        assertTrue(result.IsSuccess());
    }


}
