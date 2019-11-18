package ru.rosbank.javaschool.repository;


import ru.rosbank.javaschool.domain.order.Order;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepositoryInterface {
    Order create(Order order);

    Collection<Order> getAll();

    Optional<Order> getById(int id);

    Order update(Order newOrder);

    boolean removeById(int id);
}
