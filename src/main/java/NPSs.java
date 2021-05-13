import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPSs implements Serializable {
    private final List<NPS> items;

    public NPSs() {
        items = new ArrayList<NPS>();
    }

    public boolean add(NPS item) {
        if (item == null)
            throw new RuntimeException(Color.RED + "Ошибка при добавлении предмета." + Color.RESET);
        return items.add(item);
    }

    public boolean remove(NPS item) {
        if (item == null)
            throw new RuntimeException(Color.RED + "Ошибка при удалении предмета." + Color.RESET);
        return items.remove(item);
    }

    public void show() {
        if (!items.isEmpty()) {
            System.out.println("Вас окружают:");
            items.forEach(s -> System.out.println(s.getTitle()));
        }
    }

    public NPS find(String title) {
        return items.stream().filter(s -> s.getTitle().toUpperCase().compareTo(title.toUpperCase()) == 0).findFirst().orElse(null);
    }

    public List<NPS> getItems() {
        return items;
    }
}
