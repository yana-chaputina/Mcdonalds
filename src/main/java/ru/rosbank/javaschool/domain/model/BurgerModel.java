package ru.rosbank.javaschool.domain.model;

import lombok.Data;
import ru.rosbank.javaschool.domain.dto.BurgerDetailsDto;


@Data
public class BurgerModel extends AbstractProductModel {


    public BurgerModel(int id, String name, int price, int kkal, String description) {
        super(id, name, price, kkal, description);
    }

    @Override
    public BurgerDetailsDto toDto() {
        return new BurgerDetailsDto(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription());
    }
}
