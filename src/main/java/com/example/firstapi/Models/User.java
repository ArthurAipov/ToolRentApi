package com.example.firstapi.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "blacklist", nullable = false)
    private boolean blackList = false;

    @Column(name = "approve", nullable = false)
    private boolean approve = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public User() { }
    public User(boolean approve, boolean blackList, String chatId, Role role, String name) {
        this.chatId = chatId;
        this.role = role;
        this.name = name;
    }

    public User(String alias, String name, String surname, Role role, String chatId) {
        this.alias = alias;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.chatId = chatId;
    }

    // Setters
    public void setId(Long id){ this.id = id;}
    public void setName(String name){ this.name = name;}
    public void setSurname(String surname){ this.surname = surname;}
    public void setAlias (String alias) { this.alias = alias;}
    public void setRole(Role role){this.role = role;}
    public void setChatId(String chatId){ this.chatId = chatId;}
    public void setBlackList(boolean blackList){ this.blackList = blackList;}
    public void setCreatedAt(Instant createdAt){ this.createdAt = createdAt;}
    public void setApprove(boolean approve){this.approve = approve;}

    // Getters
    public Long getId() {return this.id;}
    public String getName() { return this.name;}
    public String getAlias() { return this.alias;}
    public String getSurname() { return this.surname;}
    public String getChatId() { return this.chatId;}
    public boolean getBlackList() { return this.blackList;}
    public boolean getApprove() {return this.approve;}
    public Instant getCreatedAt(){return this.createdAt;}
    public Role getRole(){return this.role;}

}
