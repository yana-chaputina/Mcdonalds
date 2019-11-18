package ru.rosbank.javaschool.repository;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.model.BurgerModel;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    Collection<AbstractProductModel> getProducts() {
        Collection<AbstractProductModel> listProduct = new LinkedList<>();
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel hamBurger = new BurgerModel(2, "hamburger", 30, 200, "This is hamburger");
        listProduct.add(cheeseBurger);
        listProduct.add(hamBurger);
        return listProduct;
    }

    @Test
    void create() {
        ProductRepositoryInterface productRepository = new ProductRepository();
        AbstractProductModel bigBurger = new BurgerModel(0, "bigburger", 95, 420, "This is bigburger");
        AbstractProductModel result = productRepository.create(bigBurger);
        assertEquals(bigBurger, result);
    }

    @Test
    void getAll() {
        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());

        Collection<AbstractProductModel> expected = new LinkedList<>();
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger");
        AbstractProductModel hamBurger = new BurgerModel(2, "hamburger", 30, 200, "This is hamburger");
        expected.add(cheeseBurger);
        expected.add(hamBurger);
        expected = Collections.unmodifiableCollection(expected);
        Collection<AbstractProductModel> result = productRepository.getAll();
        assertIterableEquals(result, expected);
    }

    @Test
    void getById() {

        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        Optional<AbstractProductModel> expected = Optional.of(new BurgerModel(1, "cheeseburger", 70, 250, "This is cheeseburger"));
        Optional<AbstractProductModel> result = productRepository.getById(1);
        assertEquals(result, expected);
    }

    @Test
    void getByIdRepoIsEmpty() {

        ProductRepositoryInterface productRepository = new ProductRepository();
        Optional<AbstractProductModel> result = productRepository.getById(1);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void getByIdSuchElementNotFound() {

        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        Optional<AbstractProductModel> result = productRepository.getById(10);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void update() {
        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        AbstractProductModel cheeseBurger = new BurgerModel(1, "cheeseburger", 100, 250, "This is cheeseburger");
        AbstractProductModel result = productRepository.update(cheeseBurger);
        assertEquals(cheeseBurger, result);
    }

    @Test
    void updateSuchElementNotFound() {
        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        AbstractProductModel bigBurger = new BurgerModel(0, "bigburger", 95, 420, "This is bigburger");

        assertThrows(DataSaveException.class, () -> productRepository.update(bigBurger), "Item with id = " + bigBurger.getId() + " not found.");
    }

    @Test
    void removeById() {
        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        boolean result = productRepository.removeById(1);
        assertTrue(result);
    }

    @Test
    void removeByIdSuchIdNotFound() {
        ProductRepositoryInterface productRepository = new ProductRepository(getProducts());
        boolean result = productRepository.removeById(10);
        assertTrue(!result);
    }
}