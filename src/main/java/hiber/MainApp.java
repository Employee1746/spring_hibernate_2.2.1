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

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("Audi", 100)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("Mazda", 3)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("BMW", 5)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("Mercedes", 600)));

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
        context.close();
    }
}

//CREATE TABLE my_database.cars (
//  car_id int NOT NULL AUTO_INCREMENT,
//  series int NOT NULL,
//  model varchar(25),
//  PRIMARY KEY (car_id));
//
//CREATE TABLE my_database.users (
//  id int NOT NULL AUTO_INCREMENT,
//  name varchar(25),
//  last_name varchar(25),
//  email varchar(25),
//  car_id int,
//  PRIMARY KEY (id)
//, FOREIGN KEY (car_id) REFERENCES my_database.cars(car_id));