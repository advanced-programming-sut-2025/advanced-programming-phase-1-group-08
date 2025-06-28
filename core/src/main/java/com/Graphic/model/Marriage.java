package com.Graphic.model;

import model.Enum.Commands.GameMenuCommands;

import java.util.*;

import static model.App.*;
import static model.Color_Eraser.*;

public class Marriage {
    private final User husband;
    private final User wife;
    private boolean responded = false;

    public Marriage(User husband, User wife) {
        this.husband = husband;
        this.wife = wife;
    }

    public User getHusband() {
        return husband;
    }
    public User getWife(){ return wife; }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }

    public void print() {
        System.out.println("Proposal: " + husband.getNickname() + " Wants to Marry You. Are You Willing to be his Wife?");
    }

    public boolean isBetween(User u1, User u2) {
        return (husband.equals(u1) && wife.equals(u2)) || (husband.equals(u2) && wife.equals(u1));
    }

    public static void sendProposal(User from, User to) {
        Set<User> key = new HashSet<>(Arrays.asList(from, to));
        currentGame.conversations.putIfAbsent(key, new ArrayList<>());
        currentGame.conversations.get(key).add(new MessageHandling(from, to, PURPLE+"Proposal: " + from.getNickname() + " Wants to Marry You. Do You Accept to be his Wife?"+RESET));
    }
    public static Result proposalResponse(User man, User woman) { //todo به لیست اضافه کنی
        Scanner scanner = new Scanner(System.in);
        String respond = scanner.nextLine();
        String response = GameMenuCommands.proposalRespond.getMatcher(respond).group("response");
        String tempUsername = GameMenuCommands.proposalRespond.getMatcher(respond).group("username");

        if (GameMenuCommands.proposalRespond.getMatcher(respond) == null || response == null)
            return new Result(false, RED+"Respond in Correct Format!"+RESET);
        if (!tempUsername.equals(man.getUsername()))
            return new Result(false, RED+"Username Doesn't Match!"+RESET);
        if (!(response.equalsIgnoreCase("accept") || response.equalsIgnoreCase("reject")))
            return new Result(false, RED+"Unavailable Response!"+RESET);

        boolean accepted = response.equalsIgnoreCase("accept");

        HumanCommunications f = getFriendship(man, woman);

        assert f != null;
        if (accepted) {
            f.setSUCCESSFULPropose(true);
            return f.marry();
        }
        else {
            f.reduceXP(10000);
            man.setDaysDepressedLeft(7);
            return new Result(true, RED+"You Rejected"+RESET + GREEN+" Successfully."+RESET);
        }
    }

}
