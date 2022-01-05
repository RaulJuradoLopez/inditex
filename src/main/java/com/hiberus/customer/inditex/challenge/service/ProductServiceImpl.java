package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    /**
     * Should we use compositeKey (id + name) as key, Product as key or Product.Id as key for the Maps?
     */

    //Hashtable
    Map<String, Product> normalMap = new Hashtable<String, Product>();

    /** synchronizedMap :
     * Synchronization at Object level.
     * Every read/write operation needs to acquire lock.
     * Locking the entire collection is a performance overhead.
     * This essentially gives access to only one thread to the entire map & blocks all the other threads.
     * It may cause contention.
     * SynchronizedHashMap returns Iterator, which fails-fast on concurrent modification.
     */
    Map<String, Product> synchronizedHashMap  = Collections.synchronizedMap(new HashMap<String, Product>());

    /**ConcurrentHashMap :
     * You should use ConcurrentHashMap when you need very high concurrency in your project.
     * It is thread safe without synchronizing the whole map.
     * Reads can happen very fast while write is done with a lock.
     * There is no locking at the object level.
     * The locking is at a much finer granularity at a hashmap bucket level.
     * ConcurrentHashMap doesn’t throw a ConcurrentModificationException if one thread tries to modify it while another is iterating over it.
     * ConcurrentHashMap uses multitude of locks.
     */
    Map<String, Product> concurrentProductHashMap = new ConcurrentHashMap<String, Product>();

    private static final AtomicLong idGenerator = new AtomicLong(1);


    /**
     *
     * @param product
     * @throws InvalidDataException
     */
    @Override
    public void createProduct(Product product) throws InvalidDataException {
        validateProductData(product);
        concurrentProductHashMap.put(product.getId(), product);
    }

    /**
     * As the requirements are not clear about an update for the productId, we need to pass both params.
     * If the productId is not allowed to change in time, we should create a createOrUpdateProduct method instead.
     * @param previousProductId
     * @param product
     */
    @Override
    public void updateProduct(String previousProductId, Product product) throws InvalidDataException {
        if (concurrentProductHashMap.containsKey(previousProductId) &&
                (!previousProductId.equals(product.getId()))){
            deleteProduct(previousProductId);
        }
        createProduct(product);
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
    public List<Product> searchProducts (Product searchCriteria){
       List<Product> list = concurrentProductHashMap.values().stream()
                .filter(p -> p.getId().equalsIgnoreCase(searchCriteria.getId()))
                .filter(p -> p.getName().equalsIgnoreCase(searchCriteria.getName()))
                .filter(p -> p.getDescription().equalsIgnoreCase(searchCriteria.getDescription()))
                .collect(Collectors.toList());

        return list;
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
