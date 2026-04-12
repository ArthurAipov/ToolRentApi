package com.example.firstapi.Repositories;

import com.example.firstapi.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {
}
