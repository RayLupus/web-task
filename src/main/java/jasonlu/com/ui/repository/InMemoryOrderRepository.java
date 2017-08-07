package jasonlu.com.ui.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import jasonlu.com.ui.model.Order;

/**
 * @author xiaochuan.luxc
 */
public class InMemoryOrderRepository implements OrderRepository {

    private static AtomicLong                 counter = new AtomicLong();

    private static ConcurrentMap<Long, Order> orders  = new ConcurrentHashMap<Long, Order>();

    @Override
    public Iterable<Order> findAll() {
        return orders.values();
    }

    @Override
    public Order save(Order order) {
        Long id = order.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            order.setId(id);
        }
        orders.put(id, order);
        return order;
    }

    @Override
    public Order findOrder(Long id) {
        return orders.get(id);
    }

}
