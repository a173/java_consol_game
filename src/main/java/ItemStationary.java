import java.io.Serializable;

public class ItemStationary extends Item implements Serializable {

    private Moveable    moveable;
    private String      message;

    private ItemStationary() {
        super();
    }

    public ItemStationary(String name, String description) {
        super(name, description);
        moveable = Moveable.STATIONARY;
        message = null;
    }

    public ItemStationary(String name, String description, boolean use, Integer hp, Integer money) {
        super(name, description, use, hp, money);
        moveable = Moveable.STATIONARY;
        message = null;
    }

    public ItemStationary(String name, String description, boolean use, Integer hp, Integer money, String message) {
        super(name, description, use, hp, money);
        moveable = Moveable.STATIONARY;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
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
