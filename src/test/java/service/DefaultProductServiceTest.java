package service;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.EntityAlreadeExistsException;
import ru.education.exceptions.EntityHasDetailsException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.service.Impl.DefaultProductService;
import ru.education.service.Impl.SalesPeriodService;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultProductServiceTest {


    @Autowired
    private DefaultProductService defaultProductService;

    @Autowired
    private SalesPeriodService salesPeriodService;


    @Test
    public void findAllProductsTest(){
        List<Product> products = defaultProductService.findAll();
        Assert.assertEquals(products.size(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findNullProductException(){
        defaultProductService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findIllegalArgumentProductException(){
        defaultProductService.findById("Идентифкикатор");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findNotExistsProductException(){
        defaultProductService.findById(10);
    }

    @Test
    public void findProductTest(){
        Product product= defaultProductService.findById(1);
        Assert.assertEquals(product.getName(), "car_test");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException(){
        defaultProductService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdProductException(){
        defaultProductService.create(new Product(null,"null_name"));
    }

    @Test(expected = EntityAlreadeExistsException.class)
    public void createExistsProductException(){
        defaultProductService.create(new Product(1,"exist_name"));
    }

    @Test
    public void createProductTest(){
        defaultProductService.create(new Product(3,"horse"));
        Product product= defaultProductService.findById(3);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getName(), "horse");
        defaultProductService.delete(3);
    }

    @Test(expected = EntityHasDetailsException.class)
    public void deleteHasDetailsProductException(){
        defaultProductService.delete(2);
    }

    @Test
    public void deleteProduct(){
        defaultProductService.create(new Product(3,"horse"));
        defaultProductService.delete(3);
        Assert.assertEquals(defaultProductService.findAll().size(),2);
    }

    @Test
    public void findAllSalesPeriodTest(){
        List<SalesPeriod> salesPeriods = salesPeriodService.findAll();
        Assert.assertEquals(salesPeriods.size(), 6);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findNullSalesPeriodException(){
        salesPeriodService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findIllegalArgumentSalesPeriodException(){
        salesPeriodService.findById(new SalesPeriod());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findNotExistsSalesPeriodException(){
        salesPeriodService.findById(20);
    }

    @Test
    public void findsSalesPeriod(){
        SalesPeriod salesPeriod = salesPeriodService.findById(1);
        Assert.assertNotNull(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullSalesPeriod(){
        salesPeriodService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdSalesPeriod(){
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductSalesPeriod(){
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriod.setId(Long.valueOf(7));
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductIdSalesPeriod(){
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriod.setId(Long.valueOf(7));
        salesPeriod.setProduct(new Product(null, null));
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullDateFromSalesPeriod(){
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriod.setId(Long.valueOf(8));
        salesPeriod.setProduct(defaultProductService.findById(1));
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityAlreadeExistsException.class)
    public void createExistsSalesPeriod(){
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriod.setId(Long.valueOf(1));
        salesPeriod.setProduct(defaultProductService.findById(1));
        salesPeriod.setDateFrom(new Date(1212121212121L));
        salesPeriodService.create(salesPeriod);
    }



    @Test
    public void createSalesPeriod(){
        List<SalesPeriod> salesPeriods = salesPeriodService.findAll();
        Long nextval = salesPeriodService.getNextValMySequence();
        SalesPeriod salesPeriod = new SalesPeriod();
        salesPeriod.setId(Long.valueOf(nextval+1));
        salesPeriod.setProduct(defaultProductService.findById(1));
        salesPeriod.setDateFrom(new Date(1212121212121L));
        salesPeriod.setPrice(Long.valueOf(200));
        salesPeriodService.create(salesPeriod);
        SalesPeriod salesPeriod1 = salesPeriodService.findById(nextval+1);
        Assert.assertNotNull(salesPeriod1);
        salesPeriodService.delete(nextval+1);
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistsSalesPeriod() {
        salesPeriodService.delete(15);
    }

    @Test
    public void deleteSalesPeriod() {
        salesPeriodService.delete(1);
    }

}
