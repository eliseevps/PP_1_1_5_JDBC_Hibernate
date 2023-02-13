package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable(); // Создаем таблицу в базе данных

        // Добавляем 4 User(ов)
        userService.saveUser("Pavel", "Eliseev", (byte) 27);
        userService.saveUser("Anton", "Pavlov", (byte) 30);
        userService.saveUser("Anna", "Marshmellow", (byte) 21);
        userService.saveUser("Ilya", "Kartonkov", (byte) 10);

        // Получаем всех User(ов) из базы данных в консоль
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.cleanUsersTable(); // Очистка таблицы User(ов)
        userService.dropUsersTable(); // Удаление таблицы из базы данных
    }
}
