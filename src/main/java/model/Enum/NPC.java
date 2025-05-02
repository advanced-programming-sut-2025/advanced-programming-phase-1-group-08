package model.Enum;

import model.Items;

import java.util.List;
import java.util.Map;

public enum NPC {

    Sebastian("Sebastian", List.of(sheeps_Wool, ) , Map.of("greeting", List.of("Hello amigo.", "What do you have for me?"),
                                    "gift", List.of("Oh, Thank you.", "Thanks, but... Umm, I don't like these!"),
                                    "insult", List.of("I hate you!"),
                                    "else", List.of("What? I didn't unserstand!"))),

    Abigail("Abigail", /*List of favorites*/ , Map.of("greeting", List.of("Hello amigo.", "What do you have for me?"),
            "gift", List.of("Oh, Thank you.", "Thanks, but... Umm, I don't like these!"),
            "insult", List.of("I hate you!"),
            "else", List.of("What? I didn't unserstand!"))),

    Harvey("Harvey", /*List of favorites*/ , Map.of("greeting", List.of("Hello amigo.", "What do you have for me?"),
            "gift", List.of("Oh, Thank you.", "Thanks, but... Umm, I don't like these!"),
            "insult", List.of("I hate you!"),
            "else", List.of("What? I didn't unserstand!"))),

    Lia("Lia", /*List of favorites*/ , Map.of("greeting", List.of("Hello amigo.", "What do you have for me?"),
            "gift", List.of("Oh, Thank you.", "Thanks, but... Umm, I don't like these!"),
            "insult", List.of("I hate you!"),
            "else", List.of("What? I didn't unserstand!"))),

    Robin("Robin", /*List of favorites*/ , Map.of("greet", List.of("Hello amigo.", "What do you have for me?"),
            "gift", List.of("Oh, Thank you.", "Thanks, but... Umm, I don't like these!"),
            "insult", List.of("I hate you!"),
            "else", List.of("What? I didn't unserstand!")));

    private final String name;
    private final List<Items> favoriteItem;
    private final Map<String /*Quest*/, boolean /*Done Or Not*/> Quests;
    private final Map<String, List<String>> dialogues;

    NPC(name, List<Items> favoriteItems) {
        this.name = name;
        this.favoriteItems = favoriteItems;
    }
    public List<Items> getFavoriteItems() {
        return favoriteItems;
    }
//    abstract boolean check (int id);
//    abstract void NPCGifting ();

}
