package ru.rosbank.javaschool.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;

@Data
@AllArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private int price;

    public static ProductDto from(AbstractProductModel model) {
        return new ProductDto(
                model.getId(),
                model.getName(),
                model.getPrice()
        );
    }
}
