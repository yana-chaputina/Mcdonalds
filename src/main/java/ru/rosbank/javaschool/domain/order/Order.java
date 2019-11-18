package ru.rosbank.javaschool.domain.order;

import lombok.Data;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private final List<Sale> sales;
    private int id;

    public Order(int id, List<Sale> sales) {
        this.id = id;
        this.sales = sales;
    }

    public Order(int id) {
        this.id = id;
        sales = new ArrayList<>();
    }

    public Order() {
        sales = new ArrayList<>();
    }

    public static Order copy(Order order) {
        return new Order(order.getId(), order.getSales());
    }

    public void add(AbstractProductModel product, int count) {
        for (Sale sale : sales) {
            if (sale.getProductId() == product.getId()) {
                sale.setCount(sale.getCount() + count);
            }
        }
        Sale sale = new Sale(product, count);
        sales.add(sale);
    }

    public void update(int id, int count) {
        for (Sale sale : sales) {
            if (sale.getProductId() == id) {
                sale.setCount(sale.getCount() + count);
            }
        }
        throw new DataSaveException("Item with id = " + id + " not found.");

    }

    public boolean remove(int id) {

        return sales.removeIf(o -> o.getProductId() == id);

    }

}