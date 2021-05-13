import java.io.Serializable;

public class BadNPS extends NPS implements Serializable {

    private BadNPS() {
        super();
    }

    public BadNPS(String title, Integer hp, String description, Integer money) {
        super();
        this.title = title;
        this.hp = hp;
        this.description = description;
        this.money = money;
    }
}
