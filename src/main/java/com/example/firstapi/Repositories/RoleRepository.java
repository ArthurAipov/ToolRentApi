package com.example.firstapi.Repositories;

import com.example.firstapi.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
