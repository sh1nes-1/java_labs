package lab5;

import lab5.exception.DaoException;
import lab5.model.SmartPhone;
import lab5.dao.Dao;
import lab5.dao.SmartPhoneDaoJdbc;
import lab5.exception.DatabaseConnectionException;
import lab5.connection.ConnectionBuilder;
import lab5.connection.ConnectionFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

public class TestSmartPhoneDaoJdbc {

    private Dao<SmartPhone> smartPhoneDao;

    private SmartPhone redmiNote7;
    private SmartPhone iphoneX;
    private SmartPhone redmi7;

    @BeforeTest
    public void beforeTest() {
        smartPhoneDao = new SmartPhoneDaoJdbc();
    }

    @BeforeMethod
    public void beforeMethod() throws DaoException {
        for (SmartPhone smartPhone : smartPhoneDao.findAll())
            smartPhoneDao.delete(smartPhone.getId());

        redmiNote7 = new SmartPhone.Builder()
                .setName("Xiaomi Redmi Note 7")
                .setDiagonal(6.3)
                .setColor(SmartPhone.Color.BLUE)
                .setRam(3072)
                .setReleaseDate(LocalDate.of(2019, 5, 1))
                .setPrice(4500)
                .build();

        iphoneX = new SmartPhone.Builder()
                .setName("iPhone X")
                .setDiagonal(5.8)
                .setColor(SmartPhone.Color.WHITE)
                .setRam(3072)
                .setReleaseDate(LocalDate.of(2017, 9, 12))
                .setPrice(20000)
                .build();

        redmi7 = new SmartPhone.Builder()
                .setName("Xiaomi Redmi 7")
                .setDiagonal(6.26)
                .setColor(SmartPhone.Color.BLACK)
                .setRam(2048)
                .setReleaseDate(LocalDate.of(2018, 8, 2))
                .setPrice(3600)
                .build();

        redmiNote7.setId(smartPhoneDao.insert(redmiNote7));
        iphoneX.setId(smartPhoneDao.insert(iphoneX));
        redmi7.setId(smartPhoneDao.insert(redmi7));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void databaseConnectionTest() throws DatabaseConnectionException {
        ConnectionBuilder connectionBuilder = ConnectionFactory.getConnectionBuilder();
        Connection connection = connectionBuilder.getConnection();
        Assert.assertNotNull(connection);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void findSmartPhoneByIdTest() throws DaoException {
        Optional<SmartPhone> result = smartPhoneDao.findById(redmiNote7.getId());
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get(), redmiNote7);
    }

    @Test
    public void negativeFindSmartPhoneByIdTest() throws DaoException {
        Optional<SmartPhone> result = smartPhoneDao.findById(-1L);
        Assert.assertFalse(result.isPresent());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void findAllSmartPhonesTest() throws DaoException {
        Set<SmartPhone> expected = new HashSet<>(Arrays.asList(redmiNote7, iphoneX, redmi7));
        Set<SmartPhone> actual =  new HashSet<>(smartPhoneDao.findAll());
        Assert.assertEquals(actual, expected);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 1)
    public void insertSmartPhoneTest() throws DaoException {
        SmartPhone samsungA30 = new SmartPhone.Builder()
                .setName("Samsung Galaxy A30")
                .setDiagonal(6.4)
                .setColor(SmartPhone.Color.BLACK)
                .setRam(3072)
                .setReleaseDate(LocalDate.of(2019, 6, 15))
                .setPrice(5500)
                .build();

        //TODO: ask why count
        smartPhoneDao.insert(samsungA30);
        //count

        Set<SmartPhone> expected = new HashSet<>(Arrays.asList(redmiNote7, iphoneX, redmi7, samsungA30));
        Set<SmartPhone> actual = new HashSet<>(smartPhoneDao.findAll());

        Assert.assertEquals(actual, expected);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void updateSmartPhoneTest() throws DaoException {
        SmartPhone newRedmiNote7 = new SmartPhone.Builder()
                .setId(redmiNote7.getId())
                .setName("New Redmi Note 7")
                .setColor(redmiNote7.getColor())
                .setPrice(redmiNote7.getPrice())
                .setDiagonal(redmiNote7.getDiagonal())
                .setReleaseDate(redmiNote7.getReleaseDate())
                .setRam(redmiNote7.getRam())
                .build();

        smartPhoneDao.update(newRedmiNote7);
        Optional<SmartPhone> actual = smartPhoneDao.findById(newRedmiNote7.getId());

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(actual.get(), newRedmiNote7);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void deleteSmartPhoneTest() throws DaoException {
        smartPhoneDao.delete(redmiNote7.getId());

        Set<SmartPhone> expected = new HashSet<>(Arrays.asList(iphoneX, redmi7));
        Set<SmartPhone> actual =  new HashSet<>(smartPhoneDao.findAll());

        Assert.assertEquals(actual, expected);
    }

}
