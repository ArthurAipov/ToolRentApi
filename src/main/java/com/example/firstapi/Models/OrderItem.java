package com.example.firstapi.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @SequenceGenerator(
            name = "order_item_seq",
            sequenceName = "order_items_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tool_id", nullable = false)
    private Tool tool;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "price_cents", nullable = false)
    private Long price;


    public OrderItem() { }

    public OrderItem(Order order, Tool tool, LocalDate startDate, LocalDate endDate) {
        long amountOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        this.order = order;
        this.tool = tool;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = tool.getPrice() * amountOfDays;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Tool getTool() {
        return tool;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getPrice() {
        return price;
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
