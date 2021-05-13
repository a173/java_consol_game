import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        final Logger LOGGER = LoggerFactory.getLogger(Main.class);
        LOGGER.info("Попытка запуска игры.");
        try {
            /* Setting in Game.java */
            Game game = null;
            String file = "save.txt";
            if (args.length > 1) {
                LOGGER.warn("Неверное количество параметров.");
                throw new RuntimeException("Неверное количество параметров.");
            }
            else if (args.length == 0) {
                game = Game.getInstance();
                game.startGame();
            }
            else {
                file = args[0];
                try {
                    ObjectInputStream objectIntputStream = new ObjectInputStream(new FileInputStream(file));
                    if ((game = (Game) objectIntputStream.readObject()) == null)
                        throw new IOException();
                    game.simulation();
                } catch (FileNotFoundException e) {
                    game = Game.getInstance();
                    game.startGame();
                } catch (IOException ex) {
                    LOGGER.error("Ошибка чтения или создания файла при запуске.");
                    System.out.println("Ошибка чтения или создания файла.");
                }
            }
            LOGGER.info("Сохранение игры.");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Ошибка запуска игры.");
            System.out.println("Ошибка запуска игры.");
        }
    }
}



