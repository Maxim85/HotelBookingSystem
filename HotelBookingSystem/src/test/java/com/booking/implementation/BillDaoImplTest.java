package com.booking.implementation;

import com.booking.dao.BillDaoImpl;
import com.booking.model.hotel.Bill;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maksym.
 */
public class BillDaoImplTest {
    public static final int FIRST_TEST_DATA_ID = 1;
    public static final int FIRST_TEST_DATA_FINALLBILL = 1000;

    public static final int SECOND_TEST_DATA_ID = 2;
    public static final int SECOND_TEST_DATA_FINALLBILL = 2000;

    public static final int TEST_DATA_FINAL_BILL = 3000;
    public static final int INVALID_TEST_DATA_FINAL_BILL = 0;
    static ArrayList<Bill> originalList = new ArrayList<>();
    static List<Bill> testData = new ArrayList<>();
    static BillDaoImpl dao = new BillDaoImpl();
    static Bill bill = new Bill();

    @BeforeClass
    public static void saveOriginalDataAndClearDBBeforeTest() {
        testData = createTestData();
        originalList = dao.getAll();
    }

    @AfterClass
    public static void returnOriginalDataAfterTest() {
        dao.deleteAll();
        for (Bill anOriginalList : originalList) {
            dao.add(anOriginalList);
        }
    }

    @Before
    public void AddNewDataForTest() {
        dao.deleteAll();
        fillDbWithTestData();
    }

    private void fillDbWithTestData() {
        for (Bill billToInsert : testData) {
            dao.add(billToInsert);
        }
    }

    private static List<Bill> createTestData() {
        Bill firstTestBill = new Bill();
        firstTestBill.setId(FIRST_TEST_DATA_ID);
        firstTestBill.setFinalBill(FIRST_TEST_DATA_FINALLBILL);

        Bill secondTestBill = new Bill();
        secondTestBill.setId(SECOND_TEST_DATA_ID);
        secondTestBill.setFinalBill(SECOND_TEST_DATA_FINALLBILL);

        return Arrays.asList(firstTestBill, secondTestBill);
    }

    @Test
    public void findBillByIdWithValidDataTest() {
        //1. action
        bill = dao.find(SECOND_TEST_DATA_ID);

        //2. assertion
        Assert.assertEquals(SECOND_TEST_DATA_ID, bill.getId());
        Assert.assertEquals(true, bill.getFinalBill() == SECOND_TEST_DATA_FINALLBILL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBillByIdWithInvalidDataTest() {
        //1. action
        dao.find(-FIRST_TEST_DATA_ID);
    }

    @Test
    public void updateBillWithValidDataTest() {
        //1. action
        Bill bill = new Bill();
        bill.setId(FIRST_TEST_DATA_ID);
        bill.setFinalBill(TEST_DATA_FINAL_BILL);
        dao.update(bill);

        //2. assertion
        Assert.assertEquals(FIRST_TEST_DATA_ID, bill.getId());
        Assert.assertEquals(true, bill.getFinalBill() == TEST_DATA_FINAL_BILL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateBillWithInvalidDataTest() {
        ///1. action
        Bill bill = new Bill();
        bill.setId(-FIRST_TEST_DATA_ID);
        bill.setFinalBill(INVALID_TEST_DATA_FINAL_BILL);
        dao.update(bill);
    }

    @Test
    public void addNewBillWithValidDataTest() {
        //1. action
        Bill bill = new Bill();
        bill.setId(3);
        bill.setFinalBill(TEST_DATA_FINAL_BILL);
        dao.add(bill);

        //2. assertion
        Assert.assertEquals(3, bill.getId());
        Assert.assertEquals(true, bill.getFinalBill() == TEST_DATA_FINAL_BILL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBillWithInvalidDataTest() {
        //1. action
        bill.setId(3);
        bill.setFinalBill(INVALID_TEST_DATA_FINAL_BILL);
        dao.add(bill);
    }

    @Test
    public void deleteBillByIdWithValidDataTest() {
        //1. action
        dao.delete(FIRST_TEST_DATA_ID);

        //2. assertion
        assertEquals(dao.getAll().size(), FIRST_TEST_DATA_ID);
    }

    @Test
    public void deleteAllData() {
        // 1. action
        dao.deleteAll();

        //2. assertion
        assertEquals(dao.getAll().size(), INVALID_TEST_DATA_FINAL_BILL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteBillByIdWithInvalidDataTest() {
        //1. action
        dao.delete(-FIRST_TEST_DATA_ID);
    }

    @Test
    public void getAllBidTest() {
        //1. assertion
        Assert.assertEquals(SECOND_TEST_DATA_ID, dao.getAll().size());
    }
}
