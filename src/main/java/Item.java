import java.io.Serializable;
import java.util.*;

public abstract class Item implements Serializable {

    private String  name;
    private String  description;
    private boolean use;
    private Integer hp = 0;
    private Integer money = 0;
    private final String  message = null;
    private final List<Combo> combo = new ArrayList<>();

    public Item() {}

    public Item(String name, String description, boolean use, Integer hp, Integer money) {
        this.name = name;
        this.description = description;
        this.use = use;
        this.hp = hp;
        this.money = money;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.use = false;
    }

    public void addCombo(Item object, Item result, String message, Integer money, Integer hp) {
        combo.add(new Combo(object, this, result, message, money, hp));
    }

    public List<Combo> getCombo() {
        return combo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getHp() {
        return hp;
    }

    public boolean getUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public abstract Moveable getMoveable();

    public String getMessage() { return null; };
}
