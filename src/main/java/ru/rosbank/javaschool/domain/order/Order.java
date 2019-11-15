package ru.rosbank.javaschool.domain.order;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private final List<Sale> sales;
    private int id;

    public Order(int id, List<Sale> sales) {
        this.id = id;
        this.sales = sales;
    }

    public static Order copy(Order order) {
        return new Order(order.getId(), order.getSales());
    }

    public void add(Sale sale) {
        sales.add(sale);
    }

}