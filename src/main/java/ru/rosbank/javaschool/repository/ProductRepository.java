package ru.rosbank.javaschool.repository;

import ru.rosbank.javaschool.domain.model.AbstractProductModel;
import ru.rosbank.javaschool.exception.DataSaveException;

import java.util.*;

public class ProductRepository implements ProductRepositoryInterface {

    private final Collection<AbstractProductModel> products;
    private int nextId;

    public ProductRepository(Collection<AbstractProductModel> products) {
        this.products = products;
        nextId = products.size() + 1;
    }

    public ProductRepository() {
        this(new LinkedList<>());
    }

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
