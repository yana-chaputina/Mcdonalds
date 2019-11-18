package ru.rosbank.javaschool.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;
import ru.rosbank.javaschool.domain.dto.BurgerDetailsDto;
import ru.rosbank.javaschool.domain.dto.ProductDto;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.model.BurgerModel;
import ru.rosbank.javaschool.domain.model.DrinkModel;
import ru.rosbank.javaschool.domain.model.PotatoModel;
import ru.rosbank.javaschool.domain.order.Order;
import ru.rosbank.javaschool.exception.DataNotFoundException;
import ru.rosbank.javaschool.exception.InvalidDataException;
import ru.rosbank.javaschool.repository.OrderRepositoryInterface;
import ru.rosbank.javaschool.repository.ProductRepositoryInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Test
    void saveProductThrowExceptWhenIdIsNegative() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(-3, "cheeseburger", 70, 250, "This is cheeseburger");

        assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails), "Id can't be negative");
    }

    @Test
    void saveProductThrowExceptWhenNameIsNull() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(0, null, 70, 250, "This is cheeseburger");

        assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails), "Product must have a name");
    }

    @Test
    void saveProductThrowExceptWhenPriceIsNegative() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(0, "cheeseburger", -23, 250, "This is cheeseburger");

        assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails), "Price can't be negative");
    }

    @Test
    void saveProductThrowExceptWhenKkalIsNegative() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(0, "cheeseburger", 70, -38, "This is cheeseburger");

        assertThrows(InvalidDataException.class,
                () -> service.saveProduct(cheeseBurgerDetails), "The energy value can't be negative");
    }

    @Test
    void saveProductUpdate() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(1, "cheeseburger", 70, 250, "This is cheeseburger");

        service.saveProduct(cheeseBurgerDetails);

        verify(productRepoMock, times(0)).create(cheeseBurgerDetails.toModel());
        verify(productRepoMock, times(1)).update(cheeseBurgerDetails.toModel());
    }

    @Test
    void saveProductCreate() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        AbstractProductDetailsDto cheeseBurgerDetails = new BurgerDetailsDto(0, "cheeseburger", 70, 250, "This is cheeseburger");

        service.saveProduct(cheeseBurgerDetails);

        verify(productRepoMock, times(1)).create(cheeseBurgerDetails.toModel());
        verify(productRepoMock, times(0)).update(cheeseBurgerDetails.toModel());
    }

    @Test
    void saveOrderThrowExceptWhenIdIsNegative() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        Order order = new Order(-4);

        assertThrows(InvalidDataException.class,
                () -> service.saveOrder(order), "Id can't be negative");
    }

    @Test
    void saveOrderThrowExceptWhenSalesIsNull() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        Order order = new Order(7, null);

        assertThrows(InvalidDataException.class,
                () -> service.saveOrder(order), "Order must have sales");
    }

    @Test
    void saveOrderUpdate() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order order = new Order(1);
        order.add(cheeseBurger, 2);

        service.saveOrder(order);

        verify(orderRepoMock, times(0)).create(order);
        verify(orderRepoMock, times(1)).update(order);
    }

    @Test
    void saveOrderCreate() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order order = new Order(0);
        order.add(cheeseBurger, 2);

        service.saveOrder(order);

        verify(orderRepoMock, times(1)).create(order);
        verify(orderRepoMock, times(0)).update(order);
    }

    @Test
    void searchProductByText() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.getAll()).thenReturn(new ArrayList<>());
        String text = "burger";

        Collection<ProductDto> result = service.searchProduct(text);
        assertNotNull(result);
    }

    @Test
    void searchProductNotFound() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.getAll()).thenReturn(Collections.emptyList());
        String text = "banana";

        Collection<ProductDto> result = service.searchProduct(text);
        assertNotNull(result);
    }

    @Test
    void getProductInCategory() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.getAll()).thenReturn(new ArrayList<>());

        String text = "burger";
        Collection<ProductDto> result = service.getProductInCategory(text);
        assertNotNull(result);
    }

    @Test
    void getProductInCategoryNotFound() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.getAll()).thenReturn(Collections.emptyList());

        String text = "toy";
        Collection<ProductDto> result = service.getProductInCategory(text);
        assertNotNull(result);
    }

    @Test
    void getProductById() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.of(new BurgerModel()));

        AbstractProductDetailsDto result = service.getProductById(1);
        assertNotNull(result);
    }

    @Test
    void getProductByThrowExceptWhenRepoIsEmpty() {

        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.getProductById(1));
    }

    @Test
    void getProductByThrowExceptWhenIdNotFound() {

        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(productRepoMock.getById(anyInt())).thenReturn(Optional.empty());
        when(productRepoMock.getById(1)).thenReturn(Optional.of(new BurgerModel()));

        assertThrows(DataNotFoundException.class, () -> service.getProductById(2));
    }

    @Test
    void getOrderById() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(orderRepoMock.getById(1)).thenReturn(Optional.of(new Order()));

        Order result = service.getOrderById(1);
        assertNotNull(result);
    }

    @Test
    void getOrderByThrowExceptWhenIdNotFound() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(orderRepoMock.getById(anyInt())).thenReturn(Optional.empty());
        when(orderRepoMock.getById(1)).thenReturn(Optional.of(new Order()));

        assertThrows(DataNotFoundException.class, () -> service.getOrderById(2));

    }

    @Test
    void getOrderByThrowExceptWhenRepoIsEmpty() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(orderRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.getOrderById(1));

    }

    @Test
    void getAllProduct() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(productRepoMock.getAll()).thenReturn(new ArrayList<>());

        Collection<ProductDto> result = service.getAllProduct();
        assertNotNull(result);
    }

    @Test
    void getAllOrder() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(orderRepoMock.getAll()).thenReturn(new ArrayList<>());

        Collection<Order> result = service.getAllOrder();
        assertNotNull(result);
    }

    @Test
    void removeOrderByIdThrowExceptWhenIdNotFound() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(orderRepoMock.removeById(anyInt())).thenReturn(false);
        when(orderRepoMock.removeById(1)).thenReturn(true);

        assertThrows(DataNotFoundException.class, () -> service.removeOrderById(2));
    }

    @Test
    void removeOrderByIdThrowExceptWhenRepoIsEmpty() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(orderRepoMock.removeById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> service.removeOrderById(1));
    }

    @Test
    void removeOrderById() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(orderRepoMock.removeById(1)).thenReturn(true);

        Boolean result = service.removeOrderById(1);
        assertTrue(result);
    }

    @Test
    void removeProductByIdThrowExceptWhenIdNotFound() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.removeById(anyInt())).thenReturn(false);
        when(productRepoMock.removeById(1)).thenReturn(true);

        assertThrows(DataNotFoundException.class, () -> service.removeProductById(2));
    }

    @Test
    void removeProductByIdThrowExceptWhenRepoIsEmpty() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);
        when(productRepoMock.removeById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> service.removeProductById(1));
    }

    @Test
    void removeProductById() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        when(productRepoMock.removeById(1)).thenReturn(true);

        Boolean result = service.removeProductById(1);
        assertTrue(result);
    }

    Collection<Order> GetOrders() {
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel hamBurger = new BurgerModel(2, "hamburger", 30, 200, "This is hamburger");
        AbstractProductModel bigBurger = new BurgerModel(3, "bigburger", 95, 420, "This is bigburger");
        AbstractProductModel frenchFries = new PotatoModel(4, "frenchfries", 50, 150, "This is french fries", 50);
        AbstractProductModel milkShake = new DrinkModel(5, "milkShake", 60, 100, "This is milkshake", 250);
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        Order order1 = new Order(0);
        order1.add(cheeseBurger, 2);
        order1.add(lemonade, 2);
        Order order2 = new Order(0);
        order2.add(milkShake, 1);
        order2.add(frenchFries, 1);
        Order order3 = new Order(0);
        order3.add(bigBurger, 1);
        order3.add(cheeseBurger, 3);
        Order order4 = new Order(0);
        order4.add(lemonade, 1);
        order4.add(cheeseBurger, 1);
        Order order5 = new Order(0);
        order5.add(hamBurger, 1);
        order5.add(frenchFries, 1);
        order5.add(bigBurger, 3);
        Order order6 = new Order(0);
        order6.add(milkShake, 1);
        order6.add(frenchFries, 1);
        Collection<Order> listOrder = new ArrayList<>();
        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);
        listOrder.add(order4);
        listOrder.add(order5);
        listOrder.add(order6);
        return listOrder;
    }

    @Test
    void getThreeMostPopularProducts() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        Mockito.when(orderRepoMock.getAll()).thenReturn(GetOrders());
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        ProductValue productValue1 = new ProductValue(1, 6);
        ProductValue productValue2 = new ProductValue(3, 4);
        ProductValue productValue3 = new ProductValue(6, 3);
        Collection<ProductValue> expected = new ArrayList<>();
        expected.add(productValue1);
        expected.add(productValue2);
        expected.add(productValue3);

        Collection<ProductValue> result = service.getThreeMostPopularProducts();
        assertEquals(expected, result);
    }

    @Test
    void getMostProfitableProduct() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        Mockito.when(orderRepoMock.getAll()).thenReturn(GetOrders());
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        ProductValue productValue1 = new ProductValue(1, 420);
        ProductValue productValue2 = new ProductValue(3, 380);
        ProductValue productValue3 = new ProductValue(4, 150);
        Collection<ProductValue> expected = new ArrayList<>();
        expected.add(productValue1);
        expected.add(productValue2);
        expected.add(productValue3);

        Collection<ProductValue> result = service.getThreeMostProfitableProduct();
        assertEquals(expected, result);
    }

    @Test
    void getMostPopularPair() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        Mockito.when(orderRepoMock.getAll()).thenReturn(GetOrders());
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        KeyValue keyValue = new KeyValue("1 6", 2);
        KeyValue result = service.getMostPopularPair();
        assertEquals(keyValue, result);
    }

    @Test
    void recommendedWithId1() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        Mockito.when(orderRepoMock.getAll()).thenReturn(GetOrders());
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        int expectedId = 6;
        int result = service.recommendedWith(1);
        assertEquals(expectedId, result);
    }

    @Test
    void recommendedWithMinId5() {
        ProductRepositoryInterface productRepoMock = mock(ProductRepositoryInterface.class);
        OrderRepositoryInterface orderRepoMock = mock(OrderRepositoryInterface.class);
        Mockito.when(orderRepoMock.getAll()).thenReturn(GetOrders());
        ProductServiceInterface service = new ProductService(productRepoMock, orderRepoMock);

        int expectedId = 4;
        int result = service.recommendedWith(5);
        assertEquals(expectedId, result);
    }
}