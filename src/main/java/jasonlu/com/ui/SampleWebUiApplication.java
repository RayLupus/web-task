
package jasonlu.com.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import jasonlu.com.ui.model.Order;
import jasonlu.com.ui.model.Product;
import jasonlu.com.ui.repository.InMemoryOrderRepository;
import jasonlu.com.ui.repository.InMemoryProductRespository;
import jasonlu.com.ui.repository.OrderRepository;
import jasonlu.com.ui.repository.ProductRepository;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleWebUiApplication {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRespository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public Converter<String, Product> productConverter() {
        return new Converter<String, Product>() {

            @Override
            public Product convert(String id) {
                return productRepository().findProduct(Long.valueOf(id));
            }
        };
    }

    @Bean
    public Converter<String, Order> orderConverter() {
        return new Converter<String, Order>() {

            @Override
            public Order convert(String id) {
                return orderRepository().findOrder(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleWebUiApplication.class, args);
    }

}
