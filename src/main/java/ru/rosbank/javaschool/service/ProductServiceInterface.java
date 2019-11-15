package ru.rosbank.javaschool.service;

import ru.rosbank.javaschool.domain.dto.AbstractProductDetailsDto;
import ru.rosbank.javaschool.domain.dto.ProductDto;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.domain.order.Order;

import java.util.Collection;

public interface ProductServiceInterface {
    AbstractProductModel saveProduct(AbstractProductDetailsDto dto);

    Order saveOrder(Order order);

    Collection<ProductDto> searchProduct(String text);

    Collection<ProductDto> getProductInCategory(String category);

    AbstractProductDetailsDto getProductById(int id);

    Order getOrderById(int id);

    Collection<ProductDto> getAllProduct();

    Collection<Order> getAllOrder();

    boolean removeOrderById(int id);

    boolean removeProductById(int id);

    Collection<ProductValue> getMostPopularProduct();

    Collection<ProductValue> getMostProfitableProduct();

    KeyValue getMostPopularPair();

    int recommendedWith(int id);
}
