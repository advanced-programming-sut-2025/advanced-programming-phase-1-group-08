package model.SaveData;

import model.Enum.Menu;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class AppTest {
    private User currentUser;
    private ArrayList<User> users;
    private Menu currentMenu;

    public AppTest(User currentUser, ArrayList<User> users, Menu currentMenu) {
        this.currentUser = currentUser;
        this.users = users;
        this.currentMenu = currentMenu;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
