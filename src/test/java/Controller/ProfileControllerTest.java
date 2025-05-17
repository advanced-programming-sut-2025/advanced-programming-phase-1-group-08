package Controller;

import model.Enum.SecurityQuestions;
import model.Result;
import model.SaveData.PasswordHashUtil;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static model.App.currentUser;
import static org.junit.jupiter.api.Assertions.*;


public class ProfileControllerTest {

    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        profileController = new ProfileController();
        currentUser = new User("Erfan2","Erfan", "erfansedaghat23@gmail.com", "Male",
                100, 100, PasswordHashUtil.hashPassword("Erfan!2321"), SecurityQuestions.FavoriteAnimal ,
                "kir");

    }

    @Test
    void changeUsernameWithSameUsername() throws IOException {
        Result result = profileController.changeUsername("Erfan2");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeUsernameWithIncorrectFormat() throws IOException {
        Result result = profileController.changeUsername("Erfan@2");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeUsernameWithExistUsername() throws IOException {
        Result result = profileController.changeUsername("Erfan3");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeUsernameSuccessfully() throws IOException {
        Result result = profileController.changeUsername("Erfan22");
        assertTrue(result.IsSuccess());
    }


    @Test
    void changeEmailWithSameEmail() throws IOException {
        Result result = profileController.changeEmail("erfansedaghat23@gmail.com");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeEmailWith2AddSign() throws IOException {
        Result result = profileController.changeEmail("erfanseda@ghat23@gmail.com");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeEmailWith2Dot() throws IOException {
        Result result = profileController.changeEmail("erfanseda..ghat23@gmail.com");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeEmailWithBadIcon() throws IOException {
        Result result = profileController.changeEmail("erfanseda>ghat23@gmail.com");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeEmailWithSuccessfully() throws IOException {
        Result result = profileController.changeEmail("erfansedaghat11@gmail.com");
        assertTrue(result.IsSuccess());
    }

    @Test
    void changeNicknameWithSameNickname() throws IOException {
        Result result = profileController.changeNickname("Erfan");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changeNicknameSuccessfully() throws IOException {
        Result result = profileController.changeNickname("Erfan22");
        assertTrue(result.IsSuccess());
    }


    @Test
    void changePassWithSamePass() throws IOException {
        Result result = profileController.changePass("Erfan!2321", "Erfan!2321");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changePassWithIncorrectFormat() throws IOException {
        Result result = profileController.changePass("Erfan!2321", "Erfan!sds");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changePassWithIncorrectOldPass() throws IOException {
        Result result = profileController.changePass("Erfan!2223", "Erfan!2323");
        assertFalse(result.IsSuccess());
    }
    @Test
    void changePassSuccessfully() throws IOException {
        Result result = profileController.changePass("Erfan!2222", "Erfan!2321");
        assertTrue(result.IsSuccess());
    }


}
