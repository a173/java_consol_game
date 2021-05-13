import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class PlayerTest {

    Game game = Game.getInstance();

    @Test
    @DisplayName("Проверка состояния игрока")
    public void isStatus() throws Exception {
        game.testGame();
        Assertions.assertEquals(game.getPlayer().getHp(), game.getHp());
        Assertions.assertEquals(game.getPlayer().getMoney(), game.getMoney());
    }

    @Test
    @DisplayName("Перемещение в локацию дом")
    public void isGo() throws Exception {
        game.testGame();
        game.getPlayer().go("восток");
        Assertions.assertEquals(game.getPlayer().getLocation().getName(), "Дом");
    }

    @Test
    @DisplayName("Взять предмет")
    public void isTake() throws Exception {
        game.testGame();
        game.getPlayer().go("восток");
        game.getPlayer().take("хлеб");
        Assertions.assertEquals(game.getPlayer().getInventory().getItems().size(), 1);
        Assertions.assertEquals(game.getPlayer().getLocation().getInventory().getItems().size(), 2);
    }

    @Test
    @DisplayName("Диалог")
    public void isSpeak() throws Exception {
        game.testGame();
        game.getPlayer().go("юг");
        Assertions.assertTrue(game.getPlayer().speak("Полицейский"));
        Assertions.assertEquals(game.getPlayer().getHp(), 95);
    }

    @Test
    @DisplayName("Использовать предмет из инвентаря")
    public void isUse() throws Exception {
        game.testGame();
        game.getPlayer().go("восток");
        game.getPlayer().take("хлеб");
        game.getPlayer().go("запад");
        game.getPlayer().go("юг");
        Assertions.assertTrue(game.getPlayer().speak("Полицейский"));
        game.getPlayer().use("хлеб");
        Assertions.assertEquals(game.getPlayer().getInventory().getItems().size(), 0);
        Assertions.assertEquals(game.getPlayer().getHp(), 100);
    }
}
