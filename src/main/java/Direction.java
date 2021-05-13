import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Direction {
    НАВЕРХ,
    ВНИЗ,
    ЗАПАД,
    ВОСТОК,
    СЕВЕР,
    ЮГ;

    public static ArrayList enumValid(String direction) {
        return (ArrayList) Arrays.stream(Direction.values()).filter(s->s.name().compareTo(direction) == 0).collect(Collectors.toList());
    }
}
