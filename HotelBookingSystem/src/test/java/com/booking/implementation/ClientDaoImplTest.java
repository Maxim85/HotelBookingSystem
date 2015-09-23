package com.booking.implementation;

import com.booking.dao.ClientDaoImpl;
import com.booking.model.hotel.Client;
import org.junit.*;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maksym.
 */
public class ClientDaoImplTest {

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

    static ArrayList<Client> originalList = new ArrayList<>();
    static List<Client> testData = new ArrayList<>();
    static ClientDaoImpl dao = new ClientDaoImpl();
    static Client client = new Client();

    @BeforeClass
    public static void saveOriginalDataAndClearDBBeforeTest() {
        testData = createTestData();
        originalList = dao.getAll();
    }

    @AfterClass
    public static void returnOriginalDataAfterTest() {
        dao.deleteAll();
        for (Client anOriginalList : originalList) {
            dao.add(anOriginalList);
        }
    }

    @Before
    public void AddNewDataForTest() {
        dao.deleteAll();
        fillDbWithTestData();
    }

    private void fillDbWithTestData() {
        for (Client clientToInsert : testData) {
            dao.add(clientToInsert);
        }
    }

    private static List<Client> createTestData() {
        Client firstTestAdmin = new Client();
        firstTestAdmin.setId(FIRST_TEST_DATA_ID);
        firstTestAdmin.setLogin(FIRST_TEST_DATA_LOGIN);
        firstTestAdmin.setPassword(FIRST_TEST_DATA_PASSWORD);
        firstTestAdmin.setName(FIRST_TEST_DATA_NAME);
        firstTestAdmin.setMail(FIRST_TEST_DATA_MAIL);

        Client secondTestAdmin = new Client();
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
        client = dao.find(FIRST_TEST_DATA_LOGIN);

        //2. Assertion
        Assert.assertEquals(FIRST_TEST_DATA_LOGIN, client.getLogin());
        Assert.assertEquals(FIRST_TEST_DATA_PASSWORD, client.getPassword());
        Assert.assertEquals(FIRST_TEST_DATA_ID, client.getId());
        Assert.assertEquals(FIRST_TEST_DATA_NAME, client.getName());
        Assert.assertEquals(FIRST_TEST_DATA_MAIL, client.getMail());
    }


    @Test(expected = IllegalArgumentException.class)
    public void findByLoginWithInvalidDataTest() {
        //1. action
        dao.find(BLANC);
    }

    @Test
    public void findByIdWithValidDataTest() {
        //1. action
        client = dao.find(FIRST_TEST_DATA_ID);

        //2. assertion
        Assert.assertEquals(FIRST_TEST_DATA_LOGIN, client.getLogin());
        Assert.assertEquals(FIRST_TEST_DATA_PASSWORD, client.getPassword());
        Assert.assertEquals(FIRST_TEST_DATA_ID, client.getId());
        Assert.assertEquals(FIRST_TEST_DATA_NAME, client.getName());
        Assert.assertEquals(FIRST_TEST_DATA_MAIL, client.getMail());
    }

    @Test
    public void findByIdWithInvalidId() {
        //1. action
        client = dao.find(INVALID_ID);

        //2. assertion
        Assert.assertNull(client);
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
        Client client = new Client();
        client.setId(FIRST_TEST_DATA_ID);
        client.setLogin(EXPECTED_LOGIN);
        client.setPassword(EXPECTED_PASSWORD);
        client.setName(EXPECTED_NAME);
        client.setMail(EXPECTED_MAIL);
        dao.update(client);

        //2. assertion
        Assert.assertEquals(FIRST_TEST_DATA_ID, client.getId());
        Assert.assertEquals(EXPECTED_LOGIN, client.getLogin());
        Assert.assertEquals(EXPECTED_PASSWORD, client.getPassword());
        Assert.assertEquals(EXPECTED_NAME, client.getName());
        Assert.assertEquals(EXPECTED_MAIL, client.getMail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAdminWithInvalidDataTest() {
        //1. action
        Client client = new Client();
        client.setId(-FIRST_TEST_DATA_ID);
        client.setLogin(BLANC);
        client.setPassword(BLANC);
        client.setName(BLANC);
        client.setMail(BLANC);
        dao.update(client);
    }

    @Test
    public void addNewAdminWithValidDataTest() {
        //1. action
        Client client = new Client();
        client.setId(3);
        client.setLogin(EXPECTED_LOGIN);
        client.setPassword(EXPECTED_PASSWORD);
        client.setName(EXPECTED_NAME);
        client.setMail(EXPECTED_MAIL);
        dao.add(client);

        //2. assertion
        Assert.assertEquals(3, client.getId());
        Assert.assertEquals(EXPECTED_LOGIN, client.getLogin());
        Assert.assertEquals(EXPECTED_PASSWORD, client.getPassword());
        Assert.assertEquals(EXPECTED_NAME, client.getName());
        Assert.assertEquals(EXPECTED_MAIL, client.getMail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAdminWithInvalidDataTest() {
        //1. action
        Client client = new Client();
        client.setLogin(BLANC);
        client.setPassword(BLANC);
        client.setName(BLANC);
        client.setMail(BLANC);
        dao.add(client);
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

