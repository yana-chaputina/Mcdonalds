package ru.rosbank.javaschool.repository;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.model.BurgerModel;
import ru.rosbank.javaschool.domain.model.DrinkModel;
import ru.rosbank.javaschool.domain.model.PotatoModel;
import ru.rosbank.javaschool.domain.order.Order;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    Collection<Order> getOrder() {
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel frenchFries = new PotatoModel(4, "frenchfries", 50, 150, "This is french fries", 50);
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        Order order1 = new Order(1);
        order1.add(cheeseBurger, 2);
        order1.add(lemonade, 2);
        Order order2 = new Order(2);
        order2.add(frenchFries, 1);
        Order order3 = new Order(3);
        order3.add(cheeseBurger, 3);
        Collection<Order> listOrder = new ArrayList<>();
        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);
        return listOrder;

    }

    @Test
    void create() {
        OrderRepositoryInterface orderRepository = new OrderRepository();
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order order = new Order(0);
        order.add(cheeseBurger, 2);
        Order result = orderRepository.create(order);
        assertEquals(order, result);
    }

    @Test
    void getAll() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());

        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel frenchFries = new PotatoModel(4, "frenchfries", 50, 150, "This is french fries", 50);
        AbstractProductModel lemonade = new DrinkModel(6, "lemonade", 45, 70, "This is lemonade", 300);
        Order order1 = new Order(1);
        order1.add(cheeseBurger, 2);
        order1.add(lemonade, 2);
        Order order2 = new Order(2);
        order2.add(frenchFries, 1);
        Order order3 = new Order(3);
        order3.add(cheeseBurger, 3);
        Collection<Order> expected = new ArrayList<>();
        expected.add(order1);
        expected.add(order2);
        expected.add(order3);
        expected = Collections.unmodifiableCollection(expected);

        Collection<Order> result = orderRepository.getAll();
        assertIterableEquals(expected, result);

    }

    @Test
    void getById() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order order = new Order(3);
        order.add(cheeseBurger, 3);
        Optional<Order> expected = Optional.of(order);
        Optional<Order> result = orderRepository.getById(3);
        assertEquals(expected, result);
    }

    @Test
    void getByIdRepoIsEmpty() {

        OrderRepositoryInterface productRepository = new OrderRepository();
        Optional<Order> result = productRepository.getById(1);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void getByIdSuchElementNotFound() {

        OrderRepositoryInterface productRepository = new OrderRepository();
        Optional<Order> result = productRepository.getById(10);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void update() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order expected = new Order(3);
        expected.add(cheeseBurger, 1);
        Order result = orderRepository.update(expected);
        assertEquals(expected, result);
    }

    @Test
    void updateSuchElementNotFound() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        Order expected = new Order(4);
        expected.add(cheeseBurger, 1);

        assertThrows(DataSaveException.class, () -> orderRepository.update(expected), "Item with id = " + expected.getId() + " not found.");
    }

    @Test
    void removeById() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());
        boolean result = orderRepository.removeById(1);
        assertTrue(result);
    }

    @Test
    void removeByIdSuchIdNotFound() {
        OrderRepositoryInterface orderRepository = new OrderRepository(getOrder());
        boolean result = orderRepository.removeById(10);
        assertTrue(!result);
    }
}