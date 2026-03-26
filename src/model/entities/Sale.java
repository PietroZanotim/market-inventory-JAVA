package model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {

    private Integer id;
    private LocalDateTime date;
    private Double total = 0.0;

    private List<SaleItem> items = new ArrayList<>();

    public Sale() {
    }

    public Sale(Integer id, LocalDateTime date) {
        this.id = id;
        this.date = date;
        this.total = 0.0;
    }

    public void addItem(SaleItem item) {

        items.add(item);
        total += item.getSubTotal();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<SaleItem> getItems() {
        return items;
    }
}
