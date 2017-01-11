package dao.hibernate;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vlad on 11.11.16.
 */
public class DAOHibernateStudentTest {

    @Test
    public void testDAOHibernateGetStudentsTest(){
        assertEquals("Hnes",new DAOHibernateStudent().getAll().get(0).getLastName());
    }

    @Test
    public void testDAOHibernateLoginStudentTest(){
        assertEquals("Hnes",new DAOHibernateStudent().login("Vlad","Hnes123").getLastName());
    }

    @Test
    public void testDAOHibernateIfUserExists(){
        assertEquals(true, new DAOHibernateStudent().ifUserExists("Vlad","Hnes123"));
    }

    @Test
    public void testDAOHibernateRegistry(){
        assertEquals(true, new DAOHibernateStudent().registry("John","Black","John","Black123"));
    }

    @Test
    public void testDAOHibernateGetMark(){
        assertEquals(true, new DAOHibernateStudent().getMark("Java","Vlad","Hnes"));
    }

}