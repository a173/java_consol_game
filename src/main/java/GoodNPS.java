import java.io.Serializable;

public class GoodNPS extends NPS implements Serializable {
    private boolean multi = false;

    private GoodNPS() {
        super();
    }

    public GoodNPS(String title, Integer hp, String description, Integer money) {
        super();
        this.title = title;
        this.hp = hp;
        this.description = description;
        this.money = money;
    }

    public GoodNPS(String title, Integer hp, String description, boolean multi, Integer money) {
        super();
        this.title = title;
        this.hp = hp;
        this.money = money;
        this.description = description;
        this.multi = multi;
    }

    @Override
    public boolean getMulti() {
        return multi;
    }
}
