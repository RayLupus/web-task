
package jasonlu.com.ui.model;

import java.util.Calendar;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * @author xiaochuan.luxc
 */
public class Product {

    private Long     id;

    @NotEmpty(message = "Product Title is required.")
    private String   title;

    @NotEmpty(message = "Product Detail is required.")
    private String   detail;

    @Range(min = 1, message = "Product Amount must be greater than 0.")
    private Long     amount;

    private Calendar created = Calendar.getInstance();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreated() {
        return this.created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
