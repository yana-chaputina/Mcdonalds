package ru.rosbank.javaschool.service;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;
import ru.rosbank.javaschool.domain.dto.BurgerDetailsDto;
import ru.rosbank.javaschool.domain.dto.ProductDto;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.model.BurgerModel;
import ru.rosbank.javaschool.domain.model.DrinkModel;
import ru.rosbank.javaschool.domain.model.PotatoModel;
import ru.rosbank.javaschool.domain.order.Order;
import ru.rosbank.javaschool.domain.order.Sale;
import ru.rosbank.javaschool.exception.DataNotFoundException;
import ru.rosbank.javaschool.exception.DataSaveException;
import ru.rosbank.javaschool.exception.InvalidDataException;
import ru.rosbank.javaschool.repository.OrderRepository;
import ru.rosbank.javaschool.repository.OrderRepositoryImpl;
import ru.rosbank.javaschool.repository.ProductRepository;
import ru.rosbank.javaschool.repository.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    OrderRepository orderRepository = new OrderRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();

    public ProductServiceTest() {
        AbstractProductModel cheeseBurger = new BurgerModel(0, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel hamBurger = new BurgerModel(0, "hamburger", 30, 200, "This is hamburger");
        AbstractProductModel bigBurger = new BurgerModel(0, "bigburger", 95, 420, "This is bigburger");
        AbstractProductModel frenchFries = new PotatoModel(0, "frenchfries", 50, 150, "This is french fries", 50);
        AbstractProductModel milkShake = new DrinkModel(0, "milkShake", 60, 100, "This is milkshake", 250);
        AbstractProductModel lemonade = new DrinkModel(0, "lemonade", 45, 70, "This is lemonade", 300);
        productRepository.create(cheeseBurger);
        productRepository.create(hamBurger);
        productRepository.create(bigBurger);
        productRepository.create(frenchFries);
        productRepository.create(milkShake);
        productRepository.create(lemonade);
        List<Sale> sales1 = new ArrayList<>();
        sales1.add(new Sale(cheeseBurger, 2));
        sales1.add(new Sale(lemonade, 2));
        List<Sale> sales2 = new ArrayList<>();
        sales2.add(new Sale(bigBurger, 1));
        sales2.add(new Sale(frenchFries, 1));
        sales2.add(new Sale(milkShake, 1));
        List<Sale> sales3 = new ArrayList<>();
        sales3.add(new Sale(hamBurger, 3));
        List<Sale> sales4 = new ArrayList<>();
        sales4.add(new Sale(cheeseBurger, 2));
        sales4.add(new Sale(lemonade, 1));
        List<Sale> sales5 = new ArrayList<>();
        sales5.add(new Sale(milkShake, 1));
        sales5.add(new Sale(frenchFries, 1));
        List<Sale> sales6 = new ArrayList<>();
        sales6.add(new Sale(milkShake, 1));
        sales6.add(new Sale(cheeseBurger, 1));
        sales6.add(new Sale(frenchFries, 1));
        orderRepository.create(new Order(0, sales1));
        orderRepository.create(new Order(0, sales2));
        orderRepository.create(new Order(0, sales3));
        orderRepository.create(new Order(0, sales4));
        orderRepository.create(new Order(0, sales5));
        orderRepository.create(new Order(0, sales6));
    }

    @Test
    void saveProductIdIsNegative() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(-3, "cheeseburger", 70, 250, "This is cheeseburger");
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails));
        assertTrue(thrown.getMessage().contains("Id can't be negative"));
    }

    @Test
    void saveProductNameIsNull() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(1, null, 70, 250, "This is cheeseburger");
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails));
        assertTrue(thrown.getMessage().contains("Product must have a name"));
    }

    @Test
    void saveProductPriceIsNegative() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(1, "cheeseBurger", -256, 250, "This is cheeseburger");
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails));
        assertTrue(thrown.getMessage().contains("Price can't be negative"));
    }

    @Test
    void saveProductKkalIsNegative() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(1, "cheeseBurger", 70, -37, "This is cheeseburger");
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails));
        assertTrue(thrown.getMessage().contains("The energy value can't be negative"));
    }

    @Test
    void saveProductUpdateNotExistProduct() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(10000, "cheeseBurger", 70, 250, "This is cheeseburger");
        DataSaveException thrown = assertThrows(DataSaveException.class,
                () -> service.saveProduct(cheeseBurgerDetails));
        assertTrue(thrown.getMessage().contains("Item with id = " + cheeseBurgerDetails.getId() + " not found."));
    }

    @Test
    void saveProductUpdate() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel result = service.saveProduct(cheeseBurgerDetails);
        assertEquals(cheeseBurger, result);
    }

    @Test
    void saveProductCreate() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto chickenBurgerDetails = new BurgerDetailsDto(0, "chickenburger", 55, 210, "This is chickenburger");
        AbstractProductModel chickenBurger = new BurgerModel(0, "chikenburger", 55, 210, "This is chickenburger");
        AbstractProductModel result = service.saveProduct(chickenBurgerDetails);
        assertEquals(chickenBurger, result);
    }

    @Test
    void saveOrderIdIsNegative() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductModel cheeseBurger = new BurgerModel(0, "cheeseburger", 70, 250, "This is cheeseburger");
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(cheeseBurger, 2));
        Order order = new Order(-2, sales);
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveOrder(order));
        assertTrue(thrown.getMessage().contains("Id can't be negative"));
    }

    @Test
    void saveOrderSalesIsNull() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        Order order = new Order(7, null);
        InvalidDataException thrown = assertThrows(InvalidDataException.class,
                () -> service.saveOrder(order));
        assertTrue(thrown.getMessage().contains("Order must have sales"));
    }

    @Test
    void saveOrderUpdateNotExistOrder() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductModel cheeseBurger = new BurgerModel(0, "cheeseburger", 70, 250, "This is cheeseburger");
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(cheeseBurger, 2));
        Order order = new Order(10000, sales);
        DataSaveException thrown = assertThrows(DataSaveException.class,
                () -> service.saveOrder(order));
        assertTrue(thrown.getMessage().contains("Item with id = " + order.getId() + " not found."));
    }

    @Test
    void saveOrderUpdate() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(cheeseBurger, 3));
        sales.add(new Sale(lemonade, 2));
        Order order = new Order(1, sales);
        Order result = service.saveOrder(order);
        assertEquals(order, result);
    }

    @Test
    void saveOrderCreate() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(lemonade, 1));
        Order order = new Order(0, sales);
        Order result = service.saveOrder(order);
        assertEquals(order, result);
    }

    @Test
    void searchProductByText() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        String text = "milk";
        List<ProductDto> list = new ArrayList<>();
        ProductDto milkShakeDto = new ProductDto(5, "milkShake", 60);
        list.add(milkShakeDto);
        Collection<ProductDto> result = service.searchProduct(text);
        assertEquals(list, result);
    }

    @Test
    void searchProductNotFound() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        String text = "banana";
        Collection<ProductDto> result = service.searchProduct(text);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getProductInCategory() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        String category = "drink";
        List<ProductDto> list = new ArrayList<>();
        ProductDto milkShakeDto = new ProductDto(5, "milkShake", 60);
        ProductDto lemonadeDto = new ProductDto(6, "lemonade", 45);
        list.add(milkShakeDto);
        list.add(lemonadeDto);
        Collection<ProductDto> result = service.getProductInCategory(category);
        assertEquals(list, result);
    }

    @Test
    void getProductById() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductDetailsDto cheeseBurger = new BurgerDetailsDto(1, "burger", 70, 250, "This is cheeseburger");
        int id = 1;
        AbstractProductDetailsDto result = service.getProductById(id);
        assertEquals(cheeseBurger, result);
    }

    @Test
    void getProductByIdNotFound() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = 0;
        assertThrows(DataNotFoundException.class, () -> service.getProductById(id));
    }

    @Test
    void getOrderById() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(cheeseBurger, 2));
        sales.add(new Sale(lemonade, 2));
        Order order = new Order(1, sales);
        int id = 1;
        Order result = service.getOrderById(id);
        assertEquals(order, result);
    }

    @Test
    void getOrderByIdNotFound() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = 0;
        assertThrows(DataNotFoundException.class, () -> service.getOrderById(id));
    }

    @Test
    void getAllProduct() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        Collection<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "cheeseburger", 70));
        list.add(new ProductDto(2, "hamburger", 30));
        list.add(new ProductDto(3, "bigburger", 95));
        list.add(new ProductDto(4, "frenchfries", 50));
        list.add(new ProductDto(5, "milkShake", 60));
        list.add(new ProductDto(6, "lemonade", 45));
        Collection<ProductDto> result = service.getAllProduct();
        assertEquals(list, result);
    }

    @Test
    void getAllOrder() {
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel hamBurger = new BurgerModel(2, "hamburger", 30, 200, "This is hamburger");
        AbstractProductModel bigBurger = new BurgerModel(3, "bigburger", 95, 420, "This is bigburger");
        AbstractProductModel frenchFries = new PotatoModel(4, "frenchfries", 50, 150, "This is french fries", 50);
        AbstractProductModel milkShake = new DrinkModel(5, "milkShake", 60, 100, "This is milkshake", 250);
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        List<Sale> sales1 = new ArrayList<>();
        sales1.add(new Sale(cheeseBurger, 2));
        sales1.add(new Sale(lemonade, 2));
        List<Sale> sales2 = new ArrayList<>();
        sales2.add(new Sale(bigBurger, 1));
        sales2.add(new Sale(frenchFries, 1));
        sales2.add(new Sale(milkShake, 1));
        List<Sale> sales3 = new ArrayList<>();
        sales3.add(new Sale(hamBurger, 3));
        List<Sale> sales4 = new ArrayList<>();
        sales4.add(new Sale(cheeseBurger, 2));
        sales4.add(new Sale(lemonade, 1));
        List<Sale> sales5 = new ArrayList<>();
        sales5.add(new Sale(milkShake, 1));
        sales5.add(new Sale(frenchFries, 1));
        List<Sale> sales6 = new ArrayList<>();
        sales6.add(new Sale(milkShake, 1));
        sales6.add(new Sale(cheeseBurger, 1));
        sales6.add(new Sale(frenchFries, 1));

        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        Collection<Order> list = new ArrayList<>();
        list.add(new Order(1, sales1));
        list.add(new Order(2, sales2));
        list.add(new Order(3, sales3));
        list.add(new Order(4, sales4));
        list.add(new Order(5, sales5));
        list.add(new Order(6, sales6));
        Collection<Order> result = service.getAllOrder();
        assertEquals(list, result);
    }

    @Test
    void removeOrderByIdNotFound() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = -5;
        assertThrows(DataNotFoundException.class, () -> service.removeOrderById(id));
    }

    @Test
    void removeOrderById() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = 2;
        boolean result = service.removeOrderById(id);
        assertEquals(true, result);
    }

    @Test
    void removeProductByIdNotFound() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = -5;
        assertThrows(DataNotFoundException.class, () -> service.removeProductById(id));
    }

    @Test
    void removeProductById() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int id = 6;
        boolean result = service.removeProductById(id);
        assertEquals(true, result);
    }

    @Test
    void getMostPopularProduct() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        ProductValue productValue1 = new ProductValue(1, 5);
        ProductValue productValue2 = new ProductValue(6, 3);
        ProductValue productValue3 = new ProductValue(4, 3);
        Collection<ProductValue> list = new ArrayList<>();
        list.add(productValue1);
        list.add(productValue2);
        list.add(productValue3);
        Collection<ProductValue> result = service.getMostPopularProduct();
        assertEquals(list, result);
    }

    @Test
    void getMostProfitableProduct() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        ProductValue productValue1 = new ProductValue(1, 350);
        ProductValue productValue2 = new ProductValue(5, 180);
        ProductValue productValue3 = new ProductValue(4, 150);
        Collection<ProductValue> list = new ArrayList<>();
        list.add(productValue1);
        list.add(productValue2);
        list.add(productValue3);
        Collection<ProductValue> result = service.getMostProfitableProduct();
        assertEquals(list, result);
    }

    @Test
    void getMostPopularPair() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        KeyValue keyValue = new KeyValue("4 5", 3);
        KeyValue result = service.getMostPopularPair();
        assertEquals(keyValue, result);
    }

    @Test
    void recommendedWithId1() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int recommendedId = 6;
        int result = service.recommendedWith(1);
        assertEquals(recommendedId, result);
    }

    @Test
    void recommendedWithMinId4() {
        ProductServiceInterface service = new ProductService(productRepository, orderRepository);
        int recommendedId = 1;
        int result = service.recommendedWith(6);
        assertEquals(recommendedId, result);
    }
}