package Controller;

import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUpControllerTest {

    private RegisterController registerController;
    private LoginController loginController;

    @Before
    public void setUp() throws Exception {
        registerController = new RegisterController();
        loginController = new LoginController();
    }

    @Test
    public void testCheckNameFunction() throws IOException {
        boolean result= RegisterController.CheckName("Ariyo");
        boolean result1= RegisterController.CheckName("@@@");
        assertTrue(result);
        assertFalse(result1);
    }

    @Test
    public void testEmailCheckFunction() throws IOException {
        boolean result=RegisterController.emailCheck("ali@gmail.com");
        boolean result1= RegisterController.emailCheck("ali..@gmail.com");
        assertTrue(result);
        assertFalse(result1);
    }

    @Test
    public void testPassCheckFunction() throws IOException {
        boolean result=RegisterController.passCheck("mrk@1384");
        assertFalse(result);
    }

    @Test
    public void testStrongPassCheckFunction() throws IOException {
        String result=RegisterController.passIsStrong("mrk@1384");
        String result1= RegisterController.passIsStrong("Mrk1384");
        String result2=RegisterController.passIsStrong("Mrk@1384");
        String result3=RegisterController.passIsStrong("Mrk?1384");
        assertFalse(result==null);
        assertFalse(result1==null);
        assertFalse(result2==null);
        assertTrue(result3==null);
    }

    @Test
    public void testUsernameCheckFunction() throws IOException {
        boolean result=RegisterController.usernameCheck("Mrk1384");
        assertTrue(result);
    }

    @Test
    public void testIsUniqueCheckFunction() throws IOException {
        boolean result=RegisterController.isUnique("Erfan5");
        boolean result1= RegisterController.isUnique("Erfan@1385");
        assertFalse(result);
        assertTrue(result1);
    }


    @Test
    public void testRegisterFunction() throws IOException {
//        Result result=registerController.register("Mrk?1384" , "Mrk!1384","Mrk!1384", "Mrk","Mrk@gmail.com" , "jsknd");
//        Result result1=registerController.register("Mrk?1384" , "Mrk!1384","Mrk1384", "Mrk","Mrk@gmail.com" , "male");
//        Result result2=registerController.register("Erfan1384" , "Mrk!1384","Mrk!1384", "Mrk","Mrk@gmail.com" , "male");
        String simulatedInput = "pick question -q 1 -a Cow -c Cow\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Result x = registerController.register("Alireza-84" , "Mrk!1384","Mrk!1384", "Mrk","Mrk@gmail.com" , "male");
        System.out.println(x);
        assertTrue(x.IsSuccess());
        String simulateInput = "pick question -q 1 -a Cow -c Cow\n";
        System.setIn(new ByteArrayInputStream(simulateInput.getBytes()));

        Result y = registerController.register("Alireza-85" , "Mrk!1384","Mrk!1384", "Mrk","Mrk@gmail.com" , "jkfh");
        assertFalse(y.IsSuccess());

    }

}
