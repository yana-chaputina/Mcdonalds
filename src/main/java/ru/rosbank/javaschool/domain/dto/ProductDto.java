package ru.rosbank.javaschool.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.domain.model.AbstractProductModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
