package model;

public class HumanCommunications {
    private User player1;
    private User player2;
    private int XP; // 100 for 1, 200 for 2, 300 for 3, 400 for 4
    private int FriendshipLevel;
    private boolean BOUQUETBought = false;
    private boolean SUCCESSFULPropose = false;

    public HumanCommunications(User player2) {
        this.player1 = App.currentPlayer;
        this.player2 = player2;
        XP = 0;
        FriendshipLevel = 0;
    }

    public int getXP() {
        return XP;
    }
    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getLevel() {
        if (FriendshipLevel == 0 && getXP() >= 100) {
            increaseLevel();
            setXP(getXP() - 100);
        }
        if (FriendshipLevel == 1 && getXP() >= 200) {
            increaseLevel();
            setXP(getXP() - 200);
        }
        if (FriendshipLevel == 2 && getXP() >= 300 && BOUQUETBought) {
            increaseLevel();
            setXP(getXP() - 300);
        }
        return FriendshipLevel;
    }
    public void increaseLevel() {
        if (FriendshipLevel < 3)
            FriendshipLevel++;
    }

    // LEVEL ZERO TASKS
    public void talk() {

    }
    public void trade() {

    }

    // LEVEL ONE TASKS
    public void sendGifts() {
        if (getLevel() < 1) {
            System.out.println("You can't send Gifts in your Current Friendship Level.");
            return;
        }
        //TODO
    }

    // LEVEL TWO TASKS
    public void Hug() {
        if (getLevel() < 2) {
            System.out.println("You can't Hug in your Current Friendship Level.");
            return;
        }
        //TODO
    }

    // LEVEL THREE TASKS
    public void propose() {
        if (getLevel() < 3) {
            System.out.println("You Can't Propose in your Current Friendship Level.");
            return;
        }
        //TODO
    }

    // LEVEL FOUR
    public void marry() {
        FriendshipLevel = 4;
        //TODO زمین ها و پولهاشون مال هردو میشه
    }
}
