package ru.sapteh.service;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Role;
import ru.sapteh.model.Users;

public class UsersServiceTest extends TestCase {

    @Test
    public void testCreate() {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        Dao<Role,Integer> roleIntegerDao=new RoleService(factory);
        Users users=new Users();
        users.setFirstName("Иван");
        users.setLastName("Иванов");
        users.setLogin("ivan");
        users.setPassword("1234567");
        users.setRole(roleIntegerDao.read(3));
        usersIntegerDao.create(users);
    }
    @Test
    public void testUpdate() {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        Users users=usersIntegerDao.read(4);
        users.setFirstName("Игорь");
        usersIntegerDao.update(users);
    }
    @Test
    public void testDelete() {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        usersIntegerDao.delete(usersIntegerDao.read(4));
    }
    @Test
    public void testRead() {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        Users excepted=usersIntegerDao.read(1);
        Assert.assertNotNull(excepted);
    }
    @Test
    public void testReadByAll() {
        SessionFactory factory=new Configuration().configure().buildSessionFactory();
        Dao<Users,Integer> usersIntegerDao=new UsersService(factory);
        int actual=3;
        int excepted=usersIntegerDao.readByAll().size();
        Assert.assertEquals(excepted,actual);
    }
}