package com.example.firstapi.Models;

import com.example.firstapi.Utilities.ToolStatus;
import com.example.firstapi.Utilities.ToolStatusConvertor;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @SequenceGenerator(
            name = "tools_seq",
            sequenceName = "tools_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tools_seq")
    @Column(name = "id")
    private Long id;


    @Column(nullable = false, name = "name")
    private String name;


    @Column(name = "price_cents", nullable = false)
    private long price;

    @Column(name = "callback_data")
    private String callBackData;

    @Convert(converter = ToolStatusConvertor.class)
    @Column(name = "status", nullable = false)
    private ToolStatus status = ToolStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    public Tool () { }

    public Tool(String name, long price, ToolStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public long getPrice() {
        return this.price;
    }

    public String getCallBackData() {
        return this.callBackData;
    }

    public ToolStatus getStatus() {
        return this.status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCallBackData(String callBackData) {
        this.callBackData = callBackData;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(ToolStatus status) {
        this.status = status;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
