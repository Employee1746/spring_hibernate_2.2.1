package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
        Query queryCar = sessionFactory.getCurrentSession()
                .createQuery("FROM Car WHERE model = :myModel AND series = :mySeries");
        queryCar.setParameter("myModel", model);
        queryCar.setParameter("mySeries", series);
        Car car = (Car) queryCar.getSingleResult();

        Query query = sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE userCar = :car");
        query.setParameter("car", car);
        return (User) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}
