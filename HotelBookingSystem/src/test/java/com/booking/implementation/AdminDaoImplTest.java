package com.booking.implementation;


import com.booking.dao.AdminDaoImpl;
import com.booking.model.hotel.Admin;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maksym.
 */
public class AdminDaoImplTest {

    public static final String EXPECTED_LOGIN = "Katya";
    public static final String EXPECTED_PASSWORD = "tyuio";
    public static final String EXPECTED_NAME = "Katerina";
    public static final String EXPECTED_MAIL = "Kat@i.ua";
    public static final String FIRST_TEST_DATA_LOGIN = "Vasiliy";
    public static final String FIRST_TEST_DATA_PASSWORD = "erety";
    public static final String FIRST_TEST_DATA_NAME = "Vasil";
    public static final String FIRST_TEST_DATA_MAIL = "Vasil85@i.ua";
    public static final int FIRST_TEST_DATA_ID = 1;
    public static final String BLANC = "";
    public static final int INVALID_ID = -100;
    public static final String SECOND_TEST_DATA_LOGIN = "Roma";
    public static final String SECOND_TEST_DATA_PASSWORD = "22qwere";
    public static final String SECOND_TEST_DATA_NAME = "Roman";
    public static final String SECOND_TEST_DATA_MAIL = "Rom@i.ua";
    public static final int SECOND_TEST_DATA_ID = 2;

    static ArrayList<Admin> originalList = new ArrayList<>();
    static List<Admin> testData = new ArrayList<>();
    static AdminDaoImpl dao = new AdminDaoImpl();
    static Admin admin = new Admin();

    @BeforeClass
    public static void saveOriginalDataAndClearDBBeforeTest() {
        testData = createTestData();
        originalList = dao.getAll();
    }

    @AfterClass
    public static void returnOriginalDataAfterTest() {
        dao.deleteAll();
        for (Admin anOriginalList : originalList) {
            dao.add(anOriginalList);
        }
    }

    @Before
    public void AddNewDataForTest() {
        dao.deleteAll();
        fillDbWithTestData();
    }

    private void fillDbWithTestData() {
        for (Admin adminToInsert : testData) {
            dao.add(adminToInsert);
        }
    }

    private static List<Admin> createTestData() {
        Admin firstTestAdmin = new Admin();
        firstTestAdmin.setId(FIRST_TEST_DATA_ID);
        firstTestAdmin.setLogin(FIRST_TEST_DATA_LOGIN);
        firstTestAdmin.setPassword(FIRST_TEST_DATA_PASSWORD);
        firstTestAdmin.setName(FIRST_TEST_DATA_NAME);
        firstTestAdmin.setMail(FIRST_TEST_DATA_MAIL);

        Admin secondTestAdmin = new Admin();
        secondTestAdmin.setId(SECOND_TEST_DATA_ID);
        secondTestAdmin.setLogin(SECOND_TEST_DATA_LOGIN);
        secondTestAdmin.setPassword(SECOND_TEST_DATA_PASSWORD);
        secondTestAdmin.setName(SECOND_TEST_DATA_NAME);
        secondTestAdmin.setMail(SECOND_TEST_DATA_MAIL);

        return Arrays.asList(firstTestAdmin, secondTestAdmin);
    }

    @Test
    public void findByLoginWithValidDataTest() {
        //1. action
        admin = dao.find(FIRST_TEST_DATA_LOGIN);

        //2. Assertion
        Assert.assertEquals(FIRST_TEST_DATA_LOGIN, admin.getLogin());
        Assert.assertEquals(FIRST_TEST_DATA_PASSWORD, admin.getPassword());
        Assert.assertEquals(FIRST_TEST_DATA_ID, admin.getId());
        Assert.assertEquals(FIRST_TEST_DATA_NAME, admin.getName());
        Assert.assertEquals(FIRST_TEST_DATA_MAIL, admin.getMail());
    }


    @Test(expected = IllegalArgumentException.class)
    public void findByLoginWithInvalidDataTest() {
        //1. action
        dao.find(BLANC);
    }

    @Test
    public void findByIdWithValidDataTest() {
        //1. action
        admin = dao.find(FIRST_TEST_DATA_ID);

        //2. assertion
        Assert.assertEquals(FIRST_TEST_DATA_LOGIN, admin.getLogin());
        Assert.assertEquals(FIRST_TEST_DATA_PASSWORD, admin.getPassword());
        Assert.assertEquals(FIRST_TEST_DATA_ID, admin.getId());
        Assert.assertEquals(FIRST_TEST_DATA_NAME, admin.getName());
        Assert.assertEquals(FIRST_TEST_DATA_MAIL, admin.getMail());
    }

    @Test
    public void findByIdWithInvalidId() {
        //1. action
        admin = dao.find(INVALID_ID);

        //2. assertion
        Assert.assertNull(admin);
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void findByIdWithInvalidDataTest() {
        //1. action
        dao.find(-FIRST_TEST_DATA_ID);
    }

    @Test
    public void updateAdminWithValidDataTest() {
        //1. action
        Admin admin = new Admin();
        admin.setId(FIRST_TEST_DATA_ID);
        admin.setLogin(EXPECTED_LOGIN);
        admin.setPassword(EXPECTED_PASSWORD);
        admin.setName(EXPECTED_NAME);
        admin.setMail(EXPECTED_MAIL);
        dao.update(admin);

        //2. assertion
        Assert.assertEquals(FIRST_TEST_DATA_ID, admin.getId());
        Assert.assertEquals(EXPECTED_LOGIN, admin.getLogin());
        Assert.assertEquals(EXPECTED_PASSWORD, admin.getPassword());
        Assert.assertEquals(EXPECTED_NAME, admin.getName());
        Assert.assertEquals(EXPECTED_MAIL, admin.getMail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAdminWithInvalidDataTest() {
        //1. action
        Admin admin = new Admin();
        admin.setId(-FIRST_TEST_DATA_ID);
        admin.setLogin(BLANC);
        admin.setPassword(BLANC);
        admin.setName(BLANC);
        admin.setMail(BLANC);
        dao.update(admin);
    }

    @Test
    public void addNewAdminWithValidDataTest() {
        //1. action
        Admin admin = new Admin();
        admin.setId(3);
        admin.setLogin(EXPECTED_LOGIN);
        admin.setPassword(EXPECTED_PASSWORD);
        admin.setName(EXPECTED_NAME);
        admin.setMail(EXPECTED_MAIL);
        dao.add(admin);

        //2. assertion
        Assert.assertEquals(3, admin.getId());
        Assert.assertEquals(EXPECTED_LOGIN, admin.getLogin());
        Assert.assertEquals(EXPECTED_PASSWORD, admin.getPassword());
        Assert.assertEquals(EXPECTED_NAME, admin.getName());
        Assert.assertEquals(EXPECTED_MAIL, admin.getMail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAdminWithInvalidDataTest() {
        //1. action
        Admin admin = new Admin();
        admin.setLogin(BLANC);
        admin.setPassword(BLANC);
        admin.setName(BLANC);
        admin.setMail(BLANC);
        dao.add(admin);
    }

    @Test
    public void deleteAdminByIdWithValidDataTest() {
        //1. conditions
        dao.delete(FIRST_TEST_DATA_ID);

        //2. assertion
        assertEquals(dao.getAll().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAdminByIdWithInvalidDataTest() {
        //1. action
        dao.delete(-FIRST_TEST_DATA_ID);
    }

    @Test
    public void deleteAllData() {
        // 1. action
        dao.deleteAll();

        //2. assertion
        assertEquals(dao.getAll().size(), 0);
    }

    @Test
    public void getAllAdminTest() {
        //1. assertion
        Assert.assertEquals(2, dao.getAll().size());
    }
}
