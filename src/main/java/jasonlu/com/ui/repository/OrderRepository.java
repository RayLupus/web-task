package jasonlu.com.ui.repository;

import jasonlu.com.ui.model.Order;

/**
 * @author xiaochuan.luxc
 */
public interface OrderRepository {

    Iterable<Order> findAll();

    Order save(Order order);

    Order findOrder(Long id);
}
