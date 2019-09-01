package quiz.core;

//Don't change categories order!
public enum Category {
    Dejiny("Dejiny"),
    Umenie("Umenie"),
    Veda("Veda"),
    Literatura("Literatura"),
    Geografia("Geografia"),
    Hudba("Hudba"),
    Celebrity("Celebrity"),
    Sport("Sport"),
    Ostatne("Ostatne"),
    Biologia("Biológia"),
    Jedlo("Jedlo");

    private String name;

    Category(String input) {
        this.name = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String input) {
        this.name = input;
    }
}
