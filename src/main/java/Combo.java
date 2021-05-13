import java.io.Serializable;

public class Combo implements Serializable {
    private Item    object;
    private Item    subject;
    private Item    result;
    private String  message;
    private Integer money;
    private Integer hp;

    public Combo(Item object, Item subject, Item result, String message, Integer money, Integer hp) {
        this.object = object;
        this.subject = subject;
        this.result = result;
        this.message = message;
        this.money = money;
        this.hp = hp;
    }

    @Override
    public String toString() {
        return object.getName() + " + " + subject.getName() + " = " + result.getName();
    }

    public Item getObject() {
        return object;
    }

    public Item getSubject() {
        return subject;
    }

    public Item getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getMoney() {
        return money;
    }
}
