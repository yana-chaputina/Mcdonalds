package ru.rosbank.javaschool.repository;

import ru.rosbank.javaschool.domain.model.AbstractProductModel;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepositoryInterface {
    AbstractProductModel create(AbstractProductModel product);

    Collection<AbstractProductModel> getAll();

    Optional<AbstractProductModel> getById(int id);

    AbstractProductModel update(AbstractProductModel product);

    boolean removeById(int id);
}
