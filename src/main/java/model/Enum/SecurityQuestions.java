package model.Enum;

public enum SecurityQuestions {
    FavoriteAnimal("What's your favorite animal?"),
    FavoriteFood("What's your favorite food?"),
    FavoriteMovie("What's your favorite movie?"),
    FavoriteBook("What's your favorite book?"),
    FavoriteGame("What's your favorite game?");

    private final String questionText;

    SecurityQuestions(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }
}
