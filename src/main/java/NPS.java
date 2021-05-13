import java.io.Serializable;

public abstract class NPS implements Serializable {
    protected String  title = null;
    protected Integer hp = 0;
    protected Integer money = 0;
    protected String  description = null;

    public NPS() {}

    public String getTitle() {
        return title;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }

    public boolean getMulti() { return false; }
}
