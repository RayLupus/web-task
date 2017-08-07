
package jasonlu.com.ui.repository;

import jasonlu.com.ui.model.Product;

/**
 * @author xiaochuan.luxc
 */
public interface ProductRepository {

    Iterable<Product> findAll();

    Product save(Product product);

    Product findProduct(Long id);

}
