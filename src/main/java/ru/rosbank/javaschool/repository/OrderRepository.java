package ru.rosbank.javaschool.repository;

import ru.rosbank.javaschool.domain.order.Order;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.*;

public class OrderRepository implements OrderRepositoryInterface {

    private final Collection<Order> orders;
    private int nextId;

    public OrderRepository(Collection<Order> orders) {
        this.orders = orders;
        nextId = orders.size() + 1;
    }

    public OrderRepository() {
        this(new LinkedList<>());
    }

    @Override
    public Order create(Order order) {
        order.setId(nextId++);
        orders.add(order);
        return order;
    }

    @Override
    public Collection<Order> getAll() {
        return Collections.unmodifiableCollection(orders);
    }

    @Override
    public Optional<Order> getById(int id) {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                ;
    }

    @Override
    public Order update(Order newOrder) {
        for (Order order : orders) {
            if (order.getId() == newOrder.getId()) {
                order = Order.copy(newOrder);
                return order;
            }
        }
        throw new DataSaveException("Item with id = " + newOrder.getId() + " not found.");
    }

    @Override
    public boolean removeById(int id) {
        return orders.removeIf(o -> o.getId() == id);
    }
}
