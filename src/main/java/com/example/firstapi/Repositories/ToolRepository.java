package com.example.firstapi.Repositories;

import com.example.firstapi.Models.Tool;
import com.example.firstapi.Utilities.ToolStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findToolByCallBackData(String callBackData);

    List<Tool> findAllByStatus(ToolStatus status);
}
