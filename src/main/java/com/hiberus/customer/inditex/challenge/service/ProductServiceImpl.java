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
    Map<Product, Product> normalMap = new Hashtable<Product, Product>();

    /** synchronizedMap :
     * Synchronization at Object level.
     * Every read/write operation needs to acquire lock.
     * Locking the entire collection is a performance overhead.
     * This essentially gives access to only one thread to the entire map & blocks all the other threads.
     * It may cause contention.
     * SynchronizedHashMap returns Iterator, which fails-fast on concurrent modification.
     */
    Map<Product, Product> synchronizedHashMap  = Collections.synchronizedMap(new HashMap<Product, Product>());

    /**ConcurrentHashMap :
     * You should use ConcurrentHashMap when you need very high concurrency in your project.
     * It is thread safe without synchronizing the whole map.
     * Reads can happen very fast while write is done with a lock.
     * There is no locking at the object level.
     * The locking is at a much finer granularity at a hashmap bucket level.
     * ConcurrentHashMap doesn’t throw a ConcurrentModificationException if one thread tries to modify it while another is iterating over it.
     * ConcurrentHashMap uses multitude of locks.
     */
    Map<Product, Product> concurrentHashMap = new ConcurrentHashMap<Product, Product>();

    private static final AtomicLong idGenerator = new AtomicLong(1);

    private List<Product> searchProducts (Product searchCriteria){
       List<Product> list = normalMap.keySet().stream()
                .filter(f -> f.getName().equalsIgnoreCase("Ritchie"))
                .collect(Collectors.toList());
        return null;
    }


    @Override
    public void createProduct(Product product) throws InvalidDataException {

        if (ObjectUtils.isEmpty(product.getName())){
            throw new InvalidDataException("Product name is mandatory");
        }

        if (ObjectUtils.isEmpty(product.getId())){
            product.setId(String.valueOf(idGenerator.getAndIncrement()));
        }
        //Insert the product
    }

    @Override
    public void updateProduct(String id, Product product) {
        //TODO
    }

    @Override
    public void deleteProduct(String id) {
        //TODO
    }

    @Override
    public Collection<Product> getProducts() {
        //TODO
        return null;
    }
}
