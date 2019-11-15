package ru.rosbank.javaschool.domain.dto;

import lombok.Data;
import ru.rosbank.javaschool.domain.model.BurgerModel;

@Data
public class BurgerDetailsDto extends AbstractProductDetailsDto {


    public BurgerDetailsDto(int id, String name, int price, int kkal, String description) {
        super(id, name, price, kkal, description);
    }

    @Override
    public BurgerModel toModel() {
        return new BurgerModel(
                this.getId(),
                this.getName(),
                this.getPrice(),
                this.getKkal(),
                this.getDescription());
    }
}
