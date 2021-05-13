import java.io.Serializable;

public class Player implements Serializable {
    private Integer         hp;
    private Integer         money;
    private Location        location;
    private final Inventory inventory;
    private static Player   instance;
    private static final long serialVersionUID = 1L;

    private Player() {
        inventory = new Inventory();
        hp = 0;
        money = 0;
    }

    public static Player getInstance() {
        return instance == null ? instance = new Player() : instance;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void addMoney(Integer money) {
            this.money += money;
    }

    public void subMoney(Integer money) {
        this.money -= money;
    }

    public void addHp(Integer hp) {
        this.hp = (Math.min(this.hp + hp, 100));
    }

    public void subHp(Integer hp) {
        this.hp -= hp;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getHp() {
        return hp;
    }

    public void lookAround() {
        System.out.println("Текущая локация: " + location.getName());
        System.out.println("Описание локации:\n" + location.getDescription());
        location.getNpss().show();
        System.out.println("В локации:");
        location.getInventory().show();
        System.out.println("Доступные направления:");
        location.show();
    }

    public boolean take(String take) {
        Item inv;

        if (take.isEmpty())
            return false;
        if ((inv = location.getInventory().find(take)) == null) {
            System.out.println(Color.RED_BOLD_BRIGHT + take + " не найден в локации" + Color.RESET);
            return false;
        }
        if (inv.getMoveable() != Moveable.STATIONARY) {
            inventory.add(inv);
            location.pickUp(take);
            System.out.println(Color.GREEN_BOLD_BRIGHT + inv.getName() + " добавлен в инвентарь." + Color.RESET);
            return true;
        }
        System.out.println(Color.RED_BOLD_BRIGHT + "Стационарный объект невозможно добавить в инвентарь." + Color.RESET);
        return false;
    }

    public void Status() {
        System.out.println((money > 7500 ? Color.GREEN_BOLD : money > 2500 ? Color.YELLOW_BOLD : Color.RED_BOLD) + "На твоем счету: " + money + "$" + Color.RESET);
        System.out.println((hp > 75 ? Color.GREEN_BOLD : hp > 25 ? Color.YELLOW_BOLD : Color.RED_BOLD) + "Твое эмоциональное здоровье: " + hp + "%" + Color.RESET);
    }

    public void Inventory() {
        System.out.println("В инвентаре:");
        inventory.showPersonInventary();
    }

    public boolean speak(String obj) {
        NPS person;
        if (obj.isEmpty())
            return false;
        if ((person = location.getNpss().find(obj)) != null) {
            System.out.println(person.getDescription());
            this.addMoney(person.getMoney());
            this.addHp(person.getHp());
            location.pickNPS(person);
            Status();
            return true;
        }
        else
            System.out.println("Кто такой " + obj + "?");
        return false;
    }

    public void possibility(String item) {
        Item inv;

        if ((inv = inventory.find(item)) != null)
            if (inv.getCombo().isEmpty())
                System.out.println("Нет комбинаций");
            else
                inv.getCombo().forEach(System.out::println);
    }

    public boolean use(String inv) {
        Item buf;
        if (inv.isEmpty())
            return false;
        if ((buf = location.getInventory().find(inv)) != null) {
            if (buf instanceof ItemMobile) {
                System.out.println(Color.RED_BOLD_BRIGHT + "Прежде чем использовать " + inv + " необходимо пометить его в инвентарь." + Color.RESET);
                return false;
            }
            else if (buf.getUse()) {
                if (buf.getMoney() < 0 && this.money < Math.abs(buf.getMoney())) {
                    System.out.println(Color.RED_BOLD_BRIGHT + inv + " недостаточно денег для использования." + Color.RESET);
                    return false;
                }
                this.addHp(buf.getHp());
                this.addMoney(buf.getMoney());
                location.pickUp(inv);
                if (buf.getMessage() == null)
                    Status();
                else {
                    System.out.println(buf.getMessage());
                    setHp(0);
                }
            }
            else
                System.out.println(Color.RED_BOLD_BRIGHT + inv + " невозможно использовать, попробуйте комбинировать." + Color.RESET);
        }
        else if ((buf = inventory.find(inv)) != null) {
            if (buf.getUse()) {
                this.addHp(buf.getHp());
                this.addMoney(buf.getMoney());
                Status();
                inventory.remove(buf);
            } else
                System.out.println(Color.RED_BOLD_BRIGHT + inv + " невозможно использовать, попробуйте комбинировать." + Color.RESET);
        }
        else {
            System.out.println(Color.RED_BOLD_BRIGHT + inv + " не найден" + Color.RESET);
            return false;
        }
        return true;
    }

    public boolean use(String inv1, String inv2) {
        Item item1;
        Item item2;
        Combo temp;

        if ((item1 = location.getInventory().find(inv1)) != null || (item1 = inventory.find(inv1)) != null)
            if ((item2 = inventory.find(inv2)) != null) {
                Item finalItem = item1;
                if ((temp =  item2.getCombo().stream().filter(a -> a.getObject().equals(finalItem) && a.getSubject().equals(item2)).findFirst().orElse(null)) != null) {
                    if (!location.pickUp(inv1))
                        inventory.remove(item1);
                    inventory.remove(item2);
                    if (temp.getResult().getMoveable() == Moveable.STATIONARY)
                        location.putOn(temp.getResult());
                    else
                        inventory.add(temp.getResult());
                    System.out.println(temp.getMessage());
                    if (temp.getMoney() != 0 || temp.getHp() != 0) {
                        addMoney(temp.getMoney());
                        addHp(temp.getHp());
                        Status();
                    }
                }
                else
                    System.out.println("Ошибка комбинации.");
            }
        else
            System.out.println("Ошибка комбинации.");
        return false;
    }

    public void attack() {
        NPS person = location.getBadNpss();
        if (person != null) {
            System.out.println("Вы встретились с: " + person.getTitle());
            System.out.println(person.getDescription());
            subHp(person.getHp());
            subMoney(person.getMoney());
            location.pickNPS(person);
            Status();
        }
    }

    public boolean go(String direction) {
        Location newLocation;

        if (direction.isEmpty())
            return false;
        if ((newLocation = location.findNext(direction)) != null) {
            location = newLocation;
            System.out.println(Color.BLUE_BACKGROUND + "Вы переместились в локацию " + location.getName() + Color.RESET);
            attack();
            return true;
        }
        System.out.println(Color.RED_BOLD_BRIGHT + "Направления не существует." + Color.RESET);
        return false;
    }

    public void clearInventory() {
        inventory.getItems().clear();
    }

    /* getters for Test */

    public Location getLocation() {
        return location;
    }

    public Inventory getInventory() {
        return inventory;
    }
}