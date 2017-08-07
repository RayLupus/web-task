
package jasonlu.com.ui.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import jasonlu.com.ui.model.Order;
import jasonlu.com.ui.model.Product;
import jasonlu.com.ui.repository.OrderRepository;
import jasonlu.com.ui.repository.ProductRepository;

/**
 * @author xiaochuan.luxc
 */
@Controller
@RequestMapping("/")
public class ProductController {

    private static final Logger     LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;
    private final OrderRepository   orderRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public ModelAndView list() {
        Iterable<Product> products = this.productRepository.findAll();
        Iterable<Order> orders = this.orderRepository.findAll();
        Map<String, Iterable> model = new HashMap<String, Iterable>();
        model.put("products", products);
        model.put("orders", orders);
        return new ModelAndView("products/list", "model", model);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Product product) {
        return new ModelAndView("products/view", "product", product);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Product product) {
        return "products/form";
    }

    @RequestMapping(value = "order", method = RequestMethod.POST)
    @ResponseBody
    public String order(@RequestBody String data) {
        JSONObject result = new JSONObject();
        String errorMassage = "";
        List<Order> successOrders = new ArrayList<Order>();
        Map<String, Long> productAmount = new HashMap<String, Long>();
        try {
            JSONObject orderData = JSON.parseObject(data);
            for (Entry<String, Object> order : orderData.entrySet()) {
                Long productId = Long.valueOf(order.getKey());
                if (order.getValue() == null || StringUtils.isBlank(String.valueOf(order.getValue()))) continue;
                try {
                    Long count = Long.valueOf(String.valueOf(order.getValue()));
                    if (count <= 0) {
                        errorMassage = "Your order count is wrong!";
                        break;
                    }
                    Product product = productRepository.findProduct(productId);
                    if (product != null) {
                        if (product.getAmount() < count) {
                            errorMassage = product.getTitle() + " is not enouge amount for order";
                            break;
                        }
                        Order successOrder = new Order();
                        successOrder.setProductId(productId);
                        successOrder.setProductName(product.getTitle());
                        successOrder.setCount(count);
                        successOrders.add(successOrder);
                    }
                } catch (Throwable e) {
                    errorMassage = "Your order is not a number!";
                    break;
                }
            }
        } catch (Throwable e) {
            errorMassage = "Your order data is Error!";
            LOGGER.error(e.getMessage());
        }
        boolean success = StringUtils.isBlank(errorMassage);
        if (success) {
            for (Order order : successOrders) {
                orderRepository.save(order);
                Product product = productRepository.findProduct(order.getProductId());
                product.setAmount(product.getAmount() - order.getCount());
                productRepository.save(product);
                productAmount.put(product.getId().toString(), product.getAmount());
            }
        }
        result.put("success", success);
        result.put("errMsg", errorMassage);
        result.put("orders", successOrders);
        result.put("productAmount", productAmount);
        return JSON.toJSONString(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Product product, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("products/form", "formErrors", result.getAllErrors());
        }
        product = this.productRepository.save(product);
        redirect.addFlashAttribute("globalProduct", "Successfully created a new product");
        return new ModelAndView("redirect:/");
    }

}
