import java.io.Serializable;

public class ItemMobile extends Item implements Serializable {
    private Moveable    moveable;

    private ItemMobile() {
        super();
    }

    public ItemMobile(String name, String description) {
        super(name, description);
        moveable = Moveable.MOBILE;
    }

    public ItemMobile(String name, String description, boolean use, Integer hp, Integer money) {
        super(name, description, use, hp, money);
        moveable = Moveable.MOBILE;
    }

    @Override
    public Moveable getMoveable() {
        return moveable;
    }

    @Override
    public String toString() {
        return super.getName() + " " + super.getDescription() + " " + moveable;
    }
}
