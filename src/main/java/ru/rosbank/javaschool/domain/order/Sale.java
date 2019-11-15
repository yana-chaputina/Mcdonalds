package ru.rosbank.javaschool.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;

@Data
@AllArgsConstructor
public class Sale {
    private int productId;
    private String productName;
    private int count;
    private int price;

    public Sale(AbstractProductModel product, int count) {
        productId = product.getId();
        productName = product.getName();
        this.price = product.getPrice();
        this.count = count;
    }
}
