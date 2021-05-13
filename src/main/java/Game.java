import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game implements Serializable {
    /* Блок добавления денег и здоровья игроку */
    private final Integer         money = 10000;
    private final Integer         hp = 100;
    /* Блок добавления денег и здоровья игроку */
    /* Номер локации, с которой начинается игра */
    private final Integer         id = 1;
    /* Номер локации, с которой начинается игра */
    private final Player          player = Player.getInstance();
    private final List<Location>  location = new ArrayList<>();
    private final List<Item>      itemStationary = new ArrayList<>();
    private final List<Item>      itemMobile = new ArrayList<>();
    private final List<NPS>       GoodNPS = new ArrayList<>();
    private final List<NPS>       BadNPS = new ArrayList<>();
    private static Game           instance;

    private final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final long serialVersionUID = 1L;

    private Game() {}

    public static Game getInstance() {
        return instance == null ? instance = new Game() : instance;
    }

    private void present() {
        LOGGER.info("Старт игры.");
        /* Блок описания игры */
        System.out.println(Color.YELLOW_BOLD_BRIGHT + "****Добро пожаловать в симулятор человека, который пытается разбогатеть****" + Color.RESET);
        System.out.println("Правила игры:\nТы попал на вымешленную планету, на которой деньги можно найти на каждом шагу,\n" +
                "но, не применив смекалку и не осознав особенности местной экономики, остаться\n" +
                "с деньгами у тебя получится с трудом.\n" +
                "Итак, начнем, тебе доступны следующие команды:");
        command();
        /* Блок описания игры */
    }

    private void createItemStationary() {
        LOGGER.info("Создание стационарных объектов.");
        /* Блок создания стационарных предметов */
        itemStationary.add(new ItemStationary("Окошко", "Человек за окошком с решетками с улыбкой встречает вас."));
        itemStationary.add(new ItemStationary("Урна", "Просто урна, доверха забитая мусором", true, -5, 200));
        itemStationary.add(new ItemStationary("Перила на крыше", "Быстрый способ спуститься", true, 0, 0,
                "Несмотря на дождь, ты таки рискнул, непонятно зачем, встать на перила.\nОжидаемо, ты подскользнулся и полетел вниз, кто знает: \"что было в твоей голове?\".\n" +
                        "Теперь никто не узнает, сила притяжения оказалась сильнее тебя."));
        /* Блок создания стационарных предметов */
    }

    private void createItemMobile() {
        LOGGER.info("Создание мобильных объектов.");
        /* Блок создания мобильных предметов */
        itemMobile.add(new ItemMobile("Свидетельство на право собственности недвижимости",
                "В твоем распоряжении трехкомнаятная квартира на окраине провицниального городка, стоит недорого, зато какие пейзажи."));
        itemMobile.add(new ItemMobile("Долговые обязательства", "Твоя квартира заложена в ломбард, если хочешь ее выкупить - отправляйся в ломбард."));

        itemMobile.add(new ItemMobile("Хлеб", "Кусочек черствого серого хлеба", true, 5, 0));
        itemMobile.add(new ItemMobile("Охотничьи колбаски", "Пара охотничьих колбасок", true, 5, 0));
        itemMobile.add(new ItemMobile("Бутерброд", "Бутерброд с колбасой", true, 20, 0));
        /* Блок создания мобильных предметов */
    }

    private void createCombo() {
        LOGGER.info("Создание комбинаций объектов.");
        /* Блок создания комбинированных предметов */
        itemMobile.get(0).addCombo(itemStationary.get(0), itemMobile.get(1), "У тебя есть пачка денег и долги, ты доволен?", 10000, 20);
        itemMobile.get(1).addCombo(itemStationary.get(0), itemMobile.get(0), "Поздравляю, квартира снова в твоей собственности.", -12000, -10);
        itemMobile.get(2).addCombo(itemMobile.get(3), itemMobile.get(4), "Проверь свой инвентарь, там что-то появилось.", 0, 0);
        /* Блок создания комбинированных предметов */
    }

    private void createGoodNPS() {
        LOGGER.info("Создание положительных NPS.");
        /* Блок создания положительных NPS */
        GoodNPS.add(new GoodNPS("Полицейский", -5, "Предъявите ваши документы.", true, 0));
        GoodNPS.add(new GoodNPS("Попрошайка", 5, "Честно скажу, пропью, но за твоё здоровье.", true, -20));
        GoodNPS.add(new GoodNPS("Минниханов", 15, "Вечный президент похвалил вас за успехи в бизнесе и большой вклад в развитие республики.", 0));
        GoodNPS.add(new GoodNPS("Психиатр", 25, "Сбросил стресс и восстановил своё эмоциональное здоровье за 500$", true, -500));
        GoodNPS.add(new GoodNPS("Айрат", 45, "Встретив Айрата, сложно было отказать ему в том, чтобы пропустить с ним пару бокалов пива, тем более за его счет.\n" +
                "За обсуждением твоих успехов в школе твое настроение стало на высоте и ты поймал дзен.", 0));
        GoodNPS.add(new GoodNPS("Бармен", 15, "Виски были отменные, вы обсудили всех и вся. Ты, как обычно, поделился с барменом историей о встрече с неприятными людьми\n" +
                "на входе в заведение, тебе он, конечно, не поверил, но лицемерно сушил зубы в лицо.", -200));
        /* Блок создания положительных NPS */
    }

    private void createBadNPS() {
        LOGGER.info("Создание отрицательных NPS.");
        /* Блок создания отрицательных NPS */
        BadNPS.add(new BadNPS("Группа порядочных молодых людей в спортивных штанах", 15, "Миновать группу инетелегентных молодых людей у вас не получилось.\n" +
                "Они попросили у вас сигаретку, но так как вы не курите, диалог не завязался,\nв ходе дискуссии вы не вывезли базар и диалог перерос в другое русло.",200));
        BadNPS.add(new BadNPS("Налоговый агент", 25, "Несмотря на ваш скромный доход, агент требует вас заплатить налог", money * 13 / 100));
        /* Блок создания отрицательных NPS */
    }

    private void createMap() {
        LOGGER.info("Создание локации.");
        /* Блок создания локаций */
        location.add(new Location("Дом", "Твой! Собственный! Дом!")); // 0
        location.add(location.get(0).createNextLocation("Улица Баумана", "Главная аллея солнечного Татарстана!", "ЗАПАД")); // 1
        location.add(location.get(1).createNextLocation("Кабинет психиатра", "Подумай, почему ты тут?", "ВНИЗ")); // 2
        location.add(location.get(1).createNextLocation("Ломбард", "Здесь ты можешь или разбогатеть или лишиться всего", "ЗАПАД")); // 3
        location.add(location.get(1).createNextLocation("Кремль", "Место силы и прекрасный вид", "СЕВЕР")); // 4
        location.add(location.get(1).createNextLocation("Небоскреб", "К 2084 Татарстан стал центром вселенной, и небоскребы стали обыденностью, не удивляйся", "ЮГ")); // 5
        location.add(location.get(5).createNextLocation("Бар Жигули", "Дешевое пиво, акции 1 + 1 = 2", "ЮГ")); // 6
        location.add(location.get(5).createNextLocation("Крыша небоскреба", "Посмотреть на республики с высоты птичьего полета", "НАВЕРХ")); // 7
        location.add(location.get(5).createNextLocation("Налоговая служба", "Федеральный орган исполнительной власти, осуществляющий государственный экономический надзор", "ЗАПАД")); // 8
        /* Блок создания локаций */

        /* Блок добавления мобильных предметов в локации */
        location.get(0).putOn(itemMobile.get(0));
        location.get(0).putOn(itemMobile.get(2));
        location.get(0).putOn(itemMobile.get(3));
        /* Блок добавления мобильных предметов в локации */

        /* Блок добавления стационарных предметов в локации */
        location.get(3).putOn(itemStationary.get(0));
        location.get(1).putOn(itemStationary.get(1));
        location.get(7).putOn(itemStationary.get(2));
        /* Блок добавления стационарных предметов в локации */

        /* Блок добавления GoodNPS в локации */
        location.get(1).putNPS(GoodNPS.get(0));
        location.get(4).putNPS(GoodNPS.get(0));
        location.get(5).putNPS(GoodNPS.get(0));
        location.get(6).putNPS(GoodNPS.get(0));
        location.get(4).putNPS(GoodNPS.get(1));
        location.get(4).putNPS(GoodNPS.get(2));
        location.get(2).putNPS(GoodNPS.get(3));
        location.get(6).putNPS(GoodNPS.get(4));
        location.get(6).putNPS(GoodNPS.get(5));
        /* Блок добавления GoodNPS в локации */

        /* Блок добавления BadNPS в локации */
        location.get(6).putNPS(BadNPS.get(0));
        location.get(8).putNPS(BadNPS.get(1));
        /* Блок добавления BadNPS в локации */

        if (location.isEmpty()) {
            LOGGER.warn("Ошибка создания окружения.");
            throw new RuntimeException(Color.RED + "Ошибка создания окружения." + Color.RESET);
        }
    }

    private void command() {
        System.out.println(Color.GREEN + "ОСМОТРЕТЬСЯ ↳ " + Color.RESET + "посмотреть что ткбя окружает и какие направления тебе доступны;");
        System.out.println(Color.GREEN + "ИНВЕНТАРЬ ↳ " + Color.RESET + "посмотреть свой инвентарь;");
        System.out.println(Color.GREEN + "СТАТУС ↳ " + Color.RESET + "посмотреть состояния кошелька и эмоционального состояния;");
        System.out.println(Color.GREEN + "НАВЕРХ/ВНИЗ/ЗАПАД/ВОСТОК/СЕВЕР/ЮГ ↳ " + Color.RESET + "идти в определенном направлении;");
        System.out.println(Color.GREEN + "ПОГОВОРИТЬ ↳ объект ↳ " + Color.RESET + "поговорить с объектом.");
        System.out.println(Color.GREEN + "ВЗЯТЬ ↳ наименование предмета ↳ " + Color.RESET + "подобрать предмет из локации;");
        System.out.println(Color.GREEN + "ИСПОЛЬЗОВАТЬ ↳ объект ↳ " + Color.RESET + "использовать предмет;");
        System.out.println(Color.GREEN + "ВОЗМОЖНОСТИ ↳ объект ↳ " + Color.RESET + "посмотреть возможные комбинации объекта из инвентаря.");
        System.out.println(Color.GREEN + "КОМБО ↳ объект ↳ субъект ↳ " + Color.RESET + "оказать воздействие на объект предметом из инвентаря.");
        System.out.println(Color.GREEN + "КОМАНДЫ ↳ " + Color.RESET + "вызов подсказок команд;");
        System.out.println(Color.GREEN + "ВЫХОД ↳ " + Color.RESET + "выход из программы.");
    }

    private void createPlayer() {
        if (location.size() < id + 1) {
            LOGGER.warn("Ошибка добавления окружения.");
            throw new RuntimeException(Color.RED + "Ошибка присвоения локации игроку." + Color.RESET);
        }
        player.setLocation(location.get(id));
        player.setHp(hp);
        player.setMoney(money);
    }

    private boolean reset(String answer) {
        if (answer.compareTo("ДА") == 0) {
            LOGGER.warn("Перезапуск игры.");
            player.clearInventory();
            Location.resetStartLocation();
            location.clear();
            itemMobile.clear();
            itemStationary.clear();
            GoodNPS.clear();
            BadNPS.clear();
            startGame();
            return true;
        }
        else if (answer.compareTo("НЕТ") == 0)
            return false;
        else {
            System.out.println("Неизвестная команда");
            reset(new Scanner(System.in).nextLine().trim().toUpperCase());
        }
        return false;
    }

    public void simulation() {
        present();
        player.Status();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (player.getHp() <= 0 || player.getMoney() <= 0) {
                if (player.getHp() <= 0)
                    System.out.println("Вы эмоционально подавлены и не можете адекватно воспринимать происходящее.\n" + Color.RED_BOLD_BRIGHT + "Вы проиграли" + Color.RESET);
                else
                    System.out.println("Ваш азарт или невнимательность довели вас до банкротства.\n" + Color.RED_BOLD_BRIGHT + "Вы проиграли" + Color.RESET);
                LOGGER.info("Игра окончена");
                System.out.println("Начать сначала? (ДА/НЕТ)");
                reset(scanner.nextLine().trim().toUpperCase());
                break;
            }
            System.out.print("Введите команду: ");
            String line = scanner.nextLine().trim().toUpperCase();
            if (line.compareTo(Command.ОСМОТРЕТЬСЯ.name()) == 0)
                player.lookAround();
            else if (line.compareTo(Command.ИНВЕНТАРЬ.name()) == 0)
                player.Inventory();
            else if (line.compareTo(Command.СТАТУС.name()) == 0)
                player.Status();
            else if (!Direction.enumValid((line.toUpperCase())).isEmpty())
                player.go(line);
            else if (line.compareTo(Command.ПОГОВОРИТЬ.name()) == 0)
                player.speak(scanner.nextLine().trim());
            else if (line.compareTo(Command.ВЗЯТЬ.name()) == 0)
                player.take(scanner.nextLine().trim());
            else if (line.compareTo(Command.ИСПОЛЬЗОВАТЬ.name()) == 0)
                player.use(scanner.nextLine().trim());
            else if (line.compareTo(Command.ВОЗМОЖНОСТИ.name()) == 0)
                player.possibility(scanner.nextLine().trim());
            else if (line.compareTo(Command.КОМБО.name()) == 0)
                player.use(scanner.nextLine().trim(), scanner.nextLine().trim());
            else if (line.compareTo(Command.КОМАНДЫ.name()) == 0)
                command();
            else if (line.compareTo(Command.ВЫХОД.name()) == 0)
                break;
            else {
                System.out.println("Команды не существует");
                LOGGER.warn("Введена неверная команда.");
            }
        }
    }

    public void startGame() {
        createItemMobile();
        createItemStationary();
        createCombo();
        createGoodNPS();
        createBadNPS();
        createMap();
        createPlayer();
        simulation();
    }

    /* getters for Test */

    public void testGame() {
        player.clearInventory();
        Location.resetStartLocation();
        location.clear();
        itemMobile.clear();
        itemStationary.clear();
        GoodNPS.clear();
        BadNPS.clear();
        createItemMobile();
        createItemStationary();
        createCombo();
        createGoodNPS();
        createBadNPS();
        createMap();
        createPlayer();
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getMoney() {
        return money;
    }
}
