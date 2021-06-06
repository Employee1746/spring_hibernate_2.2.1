package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        userService.add(new User("Katya", "Ivanova", "Kate@mail.ru", new Car("Audi", 100)));
        userService.add(new User("Alexei", "Petrov", "Alex@mail.ru", new Car("Mazda", 3)));
        userService.add(new User("Kostya", "Sidorov", "Kost@mail.ru", new Car("BMW", 5)));
        userService.add(new User("Elena", "Nikolaeva", "Helen@mail.ru", new Car("Mercedes", 600)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car id = " + user.getUserCar().getCarId());
            System.out.println("Car model = " + user.getUserCar().getModel());
            System.out.println("Car series = " + user.getUserCar().getSeries());
            System.out.println();
        }
        System.out.println(userService.getUserByCar("BMW", 5));
        System.out.println(userService.getUserByCar("Audi", 100));
        context.close();
    }
}
