package Controller;

import model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
    @Test
    void testLoginWithWrongPassword() throws IOException {
        Result result = loginController.LoginRes("Erfan4", "ErfanEKhar");
        assertFalse(result.IsSuccess());
    }
    @Test
    void testLoginWithUnsignedUsername() throws IOException {
        Result result = loginController.LoginRes("ErfanSedaghat", "Errrrfan");
        assertFalse(result.IsSuccess());
    }
    @Test
    void testForgetPasswordWithUnsignedUsername() throws IOException {
        Result result = loginController.ForgotPassRes("Ariooooo");
        assertFalse(result.IsSuccess());
    }
    @Test
    void testForgetPasswordWithCorrectInfo() throws IOException {
        // سه ورودی به ترتیب: "Ariyo" سپس "Ebrahimi8?" (مثلاً بعد از یوزرنیم اولیه Erfan5)
        String simulatedInput = "Ariyo\nEbrahimi8?\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Result result = loginController.ForgotPassRes("Erfan5");

        assertTrue(result.IsSuccess());
    }


}