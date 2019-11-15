package ru.rosbank.javaschool.repository;

import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {

    private final Collection<AbstractProductModel> products = new LinkedList<>();
    private int nextId = 1;

    @Override
    public AbstractProductModel create(AbstractProductModel product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    @Override
    public Collection<AbstractProductModel> getAll() {
        return Collections.unmodifiableCollection(products);
    }

    @Override
    public Optional<AbstractProductModel> getById(int id) {
        return products.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                ;
    }

    @Override
    public AbstractProductModel update(AbstractProductModel product) {
        for (AbstractProductModel abstractProduct : products) {
            if (abstractProduct.getId() == product.getId()) {
                abstractProduct.setName(product.getName());
                abstractProduct.setPrice(product.getPrice());
                abstractProduct.setKkal(product.getKkal());
                abstractProduct.setDescription(product.getDescription());
                return product;
            }
        }
        throw new DataSaveException("Item with id = " + product.getId() + " not found.");
    }

    @Override
    public boolean removeById(int id) {
        return products.removeIf(o -> o.getId() == id);
    }
}
