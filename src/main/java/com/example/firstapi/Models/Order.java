package com.example.firstapi.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "orders_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Order() { }

    public Order(User user, boolean approved, LocalDate startDate, LocalDate endDate, Instant createdAt){
        this.user = user;
        this.approved = approved;
        this.createdAt = createdAt;
        if (endDate == null && startDate == null){
            this.startDate = null;
            this.endDate = null;
            return;
        }
        if (endDate!= null && startDate != null) {
            if (endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("start_day before end_date");
            }
            if (endDate.isBefore(LocalDate.from(Instant.now()))) {
                throw new IllegalArgumentException("end_date not available, because end_date earlier then now");
            }
            if (startDate.isBefore(LocalDate.from(Instant.now()))) {
                throw new IllegalArgumentException("start_date not available, because end_date earlier then now");
            }
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public boolean isApproved() {
        return approved;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
