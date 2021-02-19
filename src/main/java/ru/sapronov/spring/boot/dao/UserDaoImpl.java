package ru.sapronov.spring.boot.dao;

import org.springframework.stereotype.Repository;
import ru.sapronov.spring.boot.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ivan Sapronov on 16.02.2021
 * @project spring-boot
 */
@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> index(){
        List<User> users = entityManager.createQuery("SELECT u from User u").getResultList();
        return users;
    }

    @Override
    public User show(long id){
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("select u from User u where username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
