package com.Graphic.model;

public class MessageHandling {
    private final User sender;
    private final User receiver;
    private final String text;
    private boolean seen = false;

    public MessageHandling(User from, User to, String text) {
        this.sender = from;
        this.receiver = to;
        this.text = text;
    }

    public User getReceiver() {
        return receiver;
    }
    public User getSender(){ return sender; }
    public String getText() { return text; }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void print() {
        System.out.println(sender.getNickname() + " ➤ " + receiver.getNickname() + ": " + text);
    }

    public boolean isBetween(User u1, User u2) {
        return (sender.equals(u1) && receiver.equals(u2)) || (sender.equals(u2) && receiver.equals(u1));
    }
    public boolean contains(User u) {
        return sender.getUsername().equals(u.getUsername()) || receiver.getUsername().equals(u.getUsername());
    }


}
