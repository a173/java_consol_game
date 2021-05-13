import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Location implements Serializable {
    private String                          name;
    private String                          description;
    private final Inventory                 inventory;
    private final NPSs                      npss;
    private final Map<Direction, Location>  directions;
    private static int                      startLocation;

    public Location(String name, String description) {
        if (startLocation == 1)
            throw new RuntimeException(Color.RED + "Стартовых локаций не может быть несколько. Для добавления следующей локации воспользуйтесь методом createNextLocation." + Color.RESET);
        this.name = name;
        this.description = description;
        inventory = new Inventory();
        npss = new NPSs();
        directions = new HashMap<Direction, Location>();
        startLocation++;
    }

    private Location() {
        npss = new NPSs();
        inventory = new Inventory();
        directions = new HashMap<Direction, Location>();
    }

    public static void resetStartLocation() {
        Location.startLocation = 0;
    }

    private Direction reverseLocationMap(String direction) {
        Direction result;
        if (direction.compareTo(Direction.НАВЕРХ.toString()) == 0)
            result = Direction.ВНИЗ;
        else if (direction.compareTo(Direction.ВНИЗ.toString()) == 0)
            result = Direction.НАВЕРХ;
        else if (direction.compareTo(Direction.ЗАПАД.toString()) == 0)
            result = Direction.ВОСТОК;
        else if (direction.compareTo(Direction.ВОСТОК.toString()) == 0)
            result = Direction.ЗАПАД;
        else if (direction.compareTo(Direction.СЕВЕР.toString()) == 0)
            result = Direction.ЮГ;
        else
            result = Direction.СЕВЕР;
        return result;
    };

    public Location createNextLocation(String name, String description, String direction) {
        if (Direction.enumValid((direction = direction.toUpperCase())).isEmpty())
            throw new RuntimeException(Color.RED + "Направление " + direction + " отсуствует в списке доступных направлений." + Color.RESET);
        else if (findNext(direction) != null)
            throw new RuntimeException(Color.RED + "Направление " + direction + " в локации " + this.name + " уже существует." + Color.RESET);
        Location newLocation = new Location();
        newLocation.name = name;
        newLocation.description = description;
        directions.put(Direction.valueOf(direction), newLocation);
        newLocation.directions.put(reverseLocationMap(direction), this);
        return newLocation;
    }

    public Location findNext(String direction) {
        for (HashMap.Entry pair : directions.entrySet())
            if (direction.toUpperCase().compareTo(pair.getKey().toString()) == 0)
                return (Location) pair.getValue();
        return null;
    }

    public void show() {
        if (directions.isEmpty())
            System.out.println(Color.RED_BOLD_BRIGHT + "Доступных направлений нет." + Color.RESET);
        else
            directions.forEach((k, v) -> System.out.println("Направление: " + k + ". Локация: " + v.getName() + "."));;
    }

    public boolean putOn(Item inv) {
        return inventory.add(inv);
    }

    public boolean putNPS(NPS nps) { return npss.add(nps); }

    public boolean pickNPS (NPS nps) {
        if (nps instanceof BadNPS || !nps.getMulti())
            return npss.remove(nps);
        return false;
    }

    public boolean pickUp(String pick) {
        Item inv;
        if ((inv = inventory.find(pick)) == null)
            return false;
        else if (inv.getMoveable() != Moveable.STATIONARY)
            return inventory.remove(inv);
        else if (inv.getMoney() >= 0)
            inv.setUse(false);
        return true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public NPS getBadNpss() {
        return npss.getItems().stream().filter(a->a instanceof BadNPS).findFirst().orElse(null);
    }

    public NPSs getNpss() {
        return npss;
    }
}
