package com.booking.implementation;

import com.booking.dao.BidDaoImpl;
import com.booking.model.hotel.Bid;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author Maksym.
 */
public class BidDaoImplTest {
    public static final int FIRSST_TEST_DATA_ID = 50;
    public static final short FIRST_DATA_TEST_TERM = 7;
    public static final String FIRST_DATA_TEST_DATE = "2015-08-25";

    public static final int SECOND_DATA_TEST_ID = 100;
    public static final short SECOND_DATA_TEST_TERM = 14;
    public static final String SECOND_DATA_TEST_DATE = "2015-08-30";

    public static final String DATA_TEST_VALID_DATE = "2015-09-25";
    public static final String DATA_TEST_INVALID_DATE = "2015-08-05";
    public static final int TEST_DATA_INVALID_ID = -1;
    public static final short TEST_DATE_INVALID_TERM = 0;

    static ArrayList<Bid> originalList = new ArrayList<>();
    static private List<Bid> testData;
    static BidDaoImpl dao = new BidDaoImpl();
    static Bid bid = new Bid();
    static Date expectedDate = new Date();
    static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    Logger logger = Logger.getLogger(BidDaoImplTest.class.getName());

    @BeforeClass
    public static void saveOriginalDataAndClearDBBeforeTest() throws ParseException {
        testData = createTestData();
        originalList = dao.getAll();
    }

    @AfterClass
    public static void returnOriginalDataAfterTest() {
        dao.deleteAll();
        for (Bid anOriginalList : originalList) {
            dao.add(anOriginalList);
        }
    }

    @Before
    public void AddNewDataForTest() throws ParseException {
        dao.deleteAll();
        fillDbWithTestData();
    }

    private void fillDbWithTestData() {
        for (Bid aptToInsert : testData) {
            dao.add(aptToInsert);
        }
    }

    private static List<Bid> createTestData() throws ParseException {
        Bid firstTestBid = new Bid();
        firstTestBid.setId(FIRSST_TEST_DATA_ID);
        firstTestBid.setTerm(FIRST_DATA_TEST_TERM);
        firstTestBid.setArrival(formatDate.parse(FIRST_DATA_TEST_DATE));

        Bid secondTestBid = new Bid();
        secondTestBid.setId(SECOND_DATA_TEST_ID);
        secondTestBid.setTerm(SECOND_DATA_TEST_TERM);
        secondTestBid.setArrival(formatDate.parse(SECOND_DATA_TEST_DATE));

        return Arrays.asList(firstTestBid, secondTestBid);
    }

    @Test
    public void findBidByIdWithValidDataTest() throws ParseException {
        //1. action
        bid = dao.find(SECOND_DATA_TEST_ID);

        //2. assertion
        Assert.assertEquals(SECOND_DATA_TEST_ID, bid.getId());
        Assert.assertEquals(SECOND_DATA_TEST_TERM, bid.getTerm());
        Assert.assertTrue(formatDate.parse(SECOND_DATA_TEST_DATE).equals(bid.getArrival()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void findBidByIdWithInvalidDataTest() {
        //1. action
        dao.find(TEST_DATA_INVALID_ID);
    }

    @Test
    public void updateBidWithValidDataTest() throws ParseException {
        //1. action
        Bid bid = new Bid();
        bid.setId(FIRSST_TEST_DATA_ID);
        bid.setTerm((short) 45);
        bid.setArrival(formatDate.parse(SECOND_DATA_TEST_DATE));
        dao.update(bid);

        //2. assertion
        Assert.assertEquals(FIRSST_TEST_DATA_ID, bid.getId());
        Assert.assertEquals(45, bid.getTerm());
        Assert.assertTrue(formatDate.parse(SECOND_DATA_TEST_DATE).equals(bid.getArrival()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBidWithInvalidDataTest() throws ParseException {
        //1. action
        Bid bid = new Bid();
        bid.setId(TEST_DATA_INVALID_ID);
        bid.setTerm(TEST_DATE_INVALID_TERM);
        bid.setArrival(formatDate.parse(DATA_TEST_INVALID_DATE));
        dao.update(bid);
    }

    @Test
    public void addNewBidWithValidDataTest() throws ParseException {
        //1. action
        Bid bid = new Bid();
        bid.setId(150);
        bid.setTerm((short) 10);
        bid.setArrival(formatDate.parse(DATA_TEST_VALID_DATE));
        dao.add(bid);

        //2. assertion
        Assert.assertEquals(150, bid.getId());
        Assert.assertEquals(10, bid.getTerm());
        Assert.assertTrue(formatDate.parse(DATA_TEST_VALID_DATE).equals(bid.getArrival()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBidWithInvalidDataTest() throws ParseException {
        //1. action
        Bid bid = new Bid();
        bid.setId(TEST_DATA_INVALID_ID);
        bid.setTerm(TEST_DATE_INVALID_TERM);
        bid.setArrival(formatDate.parse(DATA_TEST_INVALID_DATE));
        dao.add(bid);
    }

    @Test
    public void deleteBidByIdWithValidDataTest() {
        //1. action
        dao.delete(FIRSST_TEST_DATA_ID);

        //2. assertion
        assertEquals(dao.getAll().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteBidByIdWithInvalidDataTest() {
        //1. action
        dao.delete(TEST_DATA_INVALID_ID);
    }

    @Test
    public void deleteAllData() {
        // 1. action
        dao.deleteAll();

        //2. assertion
        assertEquals(dao.getAll().size(), TEST_DATE_INVALID_TERM);
    }

    @Test
    public void getAllApartmentTest() {
        //1. assertion
        assertEquals(2, dao.getAll().size());
    }
}
