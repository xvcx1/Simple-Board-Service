package com.example.b2bservice.repository;

import com.example.b2bservice.dto.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserRepository{

    private final EntityManager em;

    public UserRepository(EntityManager em){
        this.em = em;
    }

    public List<User> findAll() {
        return null;
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User update(User entity) {
        return null;
    }

    public User delete(User entity) {
        return null;
    }

    public User findById(String id){
        User user;
        try {
            user = em.createQuery("select m from User m where m.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return user;
    }

}
