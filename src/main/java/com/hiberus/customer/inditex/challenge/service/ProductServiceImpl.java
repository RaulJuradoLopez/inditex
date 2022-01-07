package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    Map<String, Product> concurrentProductHashMap = new ConcurrentHashMap<String, Product>();
    private static final AtomicLong idGenerator = new AtomicLong(1);
    /**
     *
     * @param product
     * @return The created product
     * @throws InvalidDataException
     */
    @Override
    public Product createProduct(Product product) throws InvalidDataException {
        validateProductData(product);
        concurrentProductHashMap.put(product.getId(), product);
        return product;
    }

    /**
     * As the requirements are not clear about an update for the productId, we need to pass both params.
     * If the productId is not allowed to change in time, we should create a createOrUpdateProduct method instead.
     * @param previousProductId
     * @param product
     * @return The updated product
     * @throws InvalidDataException
     */
    @Override
    public Product updateProduct(String previousProductId, Product product) throws InvalidDataException {
        if (concurrentProductHashMap.containsKey(previousProductId) &&
                (!previousProductId.equals(product.getId()))){
            deleteProduct(previousProductId);
        }
        return createProduct(product);
    }

    @Override
    public Product deleteProduct(String id) {
        return concurrentProductHashMap.remove(id);
    }

    @Override
    public int getAvailableProducts() {
        return concurrentProductHashMap.size();
    }

    @Override
    public Collection<Product> getAllProducts() {
        return concurrentProductHashMap.values();
    }

    @Override
    public List<Product> searchProducts (Product searchCriteria){
        Predicate< Product > p1 = p -> p.getId().equalsIgnoreCase(searchCriteria.getId());
        Predicate< Product > p2 = p -> p.getName().equalsIgnoreCase(searchCriteria.getName());
        Predicate< Product > p3 = p -> p.getDescription().equalsIgnoreCase(searchCriteria.getDescription());

        Predicate < Product > completePredicate = p1.or(p2).or(p3);

        return concurrentProductHashMap.values().stream()
                 .filter(completePredicate)
                 .collect(Collectors.toList());
    }

    private void validateProductData(Product product) throws InvalidDataException {
        if (ObjectUtils.isEmpty(product.getName())){
            throw new InvalidDataException("Product name is mandatory");
        }

        if (ObjectUtils.isEmpty(product.getId())){
            product.setId(String.valueOf(idGenerator.getAndIncrement()));
        }
    }
}
