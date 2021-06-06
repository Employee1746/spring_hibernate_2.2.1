package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUserByCar(String model, int series) {
        TypedQuery<User> query;
        User user = new User();
        //try-catch на случай двух одинаковых машин у разных пользователей - вывод списка пользователей с такой машиной
        try {
            query = sessionFactory.getCurrentSession().createQuery(
                    "FROM User WHERE userCar.model= :myModel AND userCar.series= :mySeries");
            query.setParameter("myModel", model);
            query.setParameter("mySeries", series);
            user = query.getSingleResult();
        } catch (Exception e) {
            PrintListNonUniqueCarUsers(model, series);
            e.printStackTrace();
        }
        return user;
    }

    public void PrintListNonUniqueCarUsers(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "FROM User WHERE userCar.model= :myModel AND userCar.series= :mySeries");
        query.setParameter("myModel", model);
        query.setParameter("mySeries", series);
        List<User> users = query.getResultList();
        for (User user : users) {
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Car model = " + user.getUserCar().getModel());
            System.out.println("Car series = " + user.getUserCar().getSeries());
            System.out.println();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }
}
