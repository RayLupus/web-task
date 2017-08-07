
package jasonlu.com.ui.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import jasonlu.com.ui.model.Product;

/**
 * @author xiaochuan.luxc
 */
public class InMemoryProductRespository implements ProductRepository {

    private static AtomicLong                   counter  = new AtomicLong();

    private static ConcurrentMap<Long, Product> products = new ConcurrentHashMap<Long, Product>();

    static {
        Product itemA = new Product();
        itemA.setId(counter.incrementAndGet());
        itemA.setTitle("Item A");
        itemA.setDetail("Item A Detail");
        itemA.setAmount(20l);
        Product itemB = new Product();
        itemB.setId(counter.incrementAndGet());
        itemB.setTitle("Item B");
        itemB.setDetail("Item B Detail");
        itemB.setAmount(10l);

        products.put(itemA.getId(), itemA);
        products.put(itemB.getId(), itemB);
    }

    @Override
    public Iterable<Product> findAll() {
        return products.values();
    }

    @Override
    public Product save(Product product) {
        Long id = product.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            product.setId(id);
        }
        products.put(id, product);
        return product;
    }

    @Override
    public Product findProduct(Long id) {
        return products.get(id);
    }

}
