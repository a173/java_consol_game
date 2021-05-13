import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable {
    private final List<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public boolean add(Item item) {
        if (item == null)
            throw new RuntimeException(Color.RED + "Ошибка при добавлении предмета." + Color.RESET);
        return items.add(item);
    }

    public boolean remove(Item item) {
        if (item == null)
            throw new RuntimeException(Color.RED + "Ошибка при удалении предмета." + Color.RESET);
        return items.remove(item);
    }

    public void show() {
        if (items.isEmpty())
            System.out.println(Color.RED_BOLD_BRIGHT + "Предметов нет." + Color.RESET);
        else
            items.forEach(s -> System.out.println("Наименование: " + s.getName() + "\nОписание: " + s.getDescription() + "\n" + s.getMoveable()));
    }

    public void showPersonInventary() {
        if (items.isEmpty())
            System.out.println(Color.RED_BOLD_BRIGHT + "Предметов нет." + Color.RESET);
        else
            items.forEach(s -> System.out.println("Наименование: " + s.getName() + "\nОписание: " + s.getDescription()));
    }

    public Item find(String title) {
        return items.stream().filter(s -> s.getName().toUpperCase().compareTo(title.toUpperCase()) == 0).findFirst().orElse(null);
    }

    public List<Item> getItems() {
        return items;
    }
}
