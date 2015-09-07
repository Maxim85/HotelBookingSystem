package com.booking.implementation;

import com.booking.dao.ApartmentDaoImpl;
import com.booking.model.hotel.Apartment;
import com.booking.model.hotel.Type;
import org.apache.log4j.Logger;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Maksym.
 */
public class ApartmentDaoImplTest {
    public static final int FIRST_TEST_DATA_ID = 1;
    public static final String FIRST_TEST_DATA_TYPE = "LUX_1";
    public static final boolean TEST_DATA_AVAILABLE = true;
    public static final String FIRST_TEST_DATA_DATE = "2015-09-25";

    public static final int SECOND_TEST_DATA_ID = 2;
    public static final String SECOND_TEST_DATA_TYPE = "LUX_2";
    public static final String SECOND_TEST_DATA_DATE = "2015-09-30";
    
    public static final int THIRD_TEST_DATA_ID = 3;
    public static final String THIRD_TEST_DATA_TYPE = "LUX_3";
    public static final String THIRD_TEST_DATA_DATE = "2015-09-30";
    public static final String TEST_DATA_INVALID_DATE = "2015-07-20";

    static private ArrayList<Apartment> originalList = new ArrayList<>();
    static ApartmentDaoImpl dao = new ApartmentDaoImpl();
    static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    static private List<Apartment> testData;
    static Apartment apartment = new Apartment();

    @BeforeClass
    public static void saveOriginalDataAndClearDBBeforeTest() throws ParseException {
        testData = createTestData();
        originalList = dao.getAll();
    }

    @AfterClass
    public static void returnOriginalDataAfterTest() {
        dao.deleteAll();
        for (Apartment anOriginalList : originalList) {
            dao.add(anOriginalList);
        }
    }

    @Before
    public void AddNewDataForTest() throws ParseException {
        dao.deleteAll();
        fillDbWithTestData();
    }

    private void fillDbWithTestData() {
        for (Apartment aptToInsert : testData) {
            dao.add(aptToInsert);
        }
    }

    private static List<Apartment> createTestData() throws ParseException {
        Apartment firstTestApt = new Apartment();
        firstTestApt.setId(FIRST_TEST_DATA_ID);
        firstTestApt.setType(Type.getTypeByName(FIRST_TEST_DATA_TYPE));
        firstTestApt.setAvailable(TEST_DATA_AVAILABLE);
        firstTestApt.setCheckOutDate(formatDate.parse(FIRST_TEST_DATA_DATE));

        Apartment secondTestApt = new Apartment();
        secondTestApt.setId(SECOND_TEST_DATA_ID);
        secondTestApt.setType(Type.getTypeByName(SECOND_TEST_DATA_TYPE));
        secondTestApt.setAvailable(TEST_DATA_AVAILABLE);
        secondTestApt.setCheckOutDate(formatDate.parse(SECOND_TEST_DATA_DATE));

        Apartment thirdTestApt = new Apartment();
        thirdTestApt.setId(THIRD_TEST_DATA_ID);
        thirdTestApt.setType(Type.getTypeByName(THIRD_TEST_DATA_TYPE));
        thirdTestApt.setAvailable(TEST_DATA_AVAILABLE);
        thirdTestApt.setCheckOutDate(formatDate.parse(THIRD_TEST_DATA_DATE));

        return Arrays.asList(firstTestApt, secondTestApt, thirdTestApt);
    }

    @Test
    public void findByNameWithValidDataTest() throws ParseException{
        //1. action
        Apartment apartment = dao.find(SECOND_TEST_DATA_TYPE);

        //2. assertion
        assertEquals(SECOND_TEST_DATA_ID, apartment.getId());
        assertEquals(ApartmentDaoImplTest.SECOND_TEST_DATA_TYPE, apartment.getType().name());
        assertEquals(Boolean.TRUE, apartment.isAvailable());
        Assert.assertTrue(formatDate.parse(SECOND_TEST_DATA_DATE).equals(apartment.getCheckOutDate()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByNameWithInvalidDataTest() {
        //1. conditions
        String expectedTypeNameString = "";

        //2. action
        ApartmentDaoImpl dao = new ApartmentDaoImpl();
        dao.find(expectedTypeNameString);
    }

    @Test
    public void findByIdWithValidDataTest() throws ParseException {
        //1. action
        Apartment apartment = dao.find(SECOND_TEST_DATA_ID);

        //2. assertion
        assertEquals(SECOND_TEST_DATA_ID, apartment.getId());
        assertEquals(ApartmentDaoImplTest.SECOND_TEST_DATA_TYPE, apartment.getType().name());
        assertEquals(Boolean.TRUE, apartment.isAvailable());
        Assert.assertTrue(formatDate.parse(SECOND_TEST_DATA_DATE).equals(apartment.getCheckOutDate()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void findByIdWithInvalidDataTest() {
        //1. conditions
        long expectedId = -FIRST_TEST_DATA_ID;

        //2. action
        dao.find(expectedId);
    }

    @Test
    public void updateApartmentWithValidDataTest() throws ParseException {
        //1. action
        Apartment apartment =  new Apartment();
        apartment.setId(FIRST_TEST_DATA_ID);
        apartment.setType(Type.getTypeByName(FIRST_TEST_DATA_TYPE));
        apartment.setAvailable(Boolean.TRUE);
        apartment.setCheckOutDate(formatDate.parse(SECOND_TEST_DATA_DATE));
        dao.update(apartment);

        //2. assertion
        assertEquals(FIRST_TEST_DATA_ID, apartment.getId());
        assertEquals(FIRST_TEST_DATA_TYPE, apartment.getType().name());
        assertEquals(TEST_DATA_AVAILABLE, apartment.isAvailable());
        Assert.assertTrue(formatDate.parse(SECOND_TEST_DATA_DATE).equals(apartment.getCheckOutDate()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateApartmentWithInvalidDataTest() throws ParseException {
        //TODO: Create expected object, you wish to update
        //1. action
        Apartment apartment =  new Apartment();
        apartment.setId(FIRST_TEST_DATA_ID);
        apartment.setType(Type.getTypeByName(FIRST_TEST_DATA_TYPE));
        apartment.setAvailable(Boolean.TRUE);
        apartment.setCheckOutDate(formatDate.parse(TEST_DATA_INVALID_DATE));
        dao.update(apartment);
    }

    @Test
    public void addNewApartmentWithValidDataTest()throws ParseException {
        //todo: refactor me
        //1. action
        Apartment apartment = new Apartment();
        apartment.setId(4);
        apartment.setType(Type.getTypeByName(ApartmentDaoImplTest.SECOND_TEST_DATA_TYPE));
        apartment.setAvailable(Boolean.TRUE);
        apartment.setCheckOutDate(formatDate.parse(SECOND_TEST_DATA_DATE));
        dao.add(apartment);

        //2. assertion
        assertEquals(4, apartment.getId());
        assertEquals(ApartmentDaoImplTest.SECOND_TEST_DATA_TYPE, apartment.getType().name());
        assertEquals(Boolean.TRUE, apartment.isAvailable());
        Assert.assertTrue(formatDate.parse(SECOND_TEST_DATA_DATE).equals(apartment.getCheckOutDate()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void addApartmentWithInvalidDataTest() throws ParseException {
        //1. action
        Apartment apartment = new Apartment();
        apartment.setId(30);
        apartment.setType(Type.getTypeByName(""));
        apartment.setAvailable(Boolean.TRUE);
        apartment.setCheckOutDate(formatDate.parse(TEST_DATA_INVALID_DATE));
        dao.add(apartment);
    }

    @Test
    public void deleteApartmentByIdWithValidDataTest() {
        //1. action
        dao.delete(FIRST_TEST_DATA_ID);

        //2. assertion
        assertEquals(dao.getAll().size(), SECOND_TEST_DATA_ID);
    }

    @Test(expected=IllegalArgumentException.class)
    public void deleteApartmentByIdWithInvalidDataTest() {
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
    public void getAllApartmentTest() {
        //1. assertion
        assertEquals(THIRD_TEST_DATA_ID, dao.getAll().size());
    }
}





