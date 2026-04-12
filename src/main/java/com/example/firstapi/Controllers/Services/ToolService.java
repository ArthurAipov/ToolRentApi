package com.example.firstapi.Controllers.Services;

import com.example.firstapi.Controllers.dtos.ToolDTO;
import com.example.firstapi.Models.Tool;
import com.example.firstapi.Repositories.ToolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToolService {
    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    // GET REQUESTS
    @Transactional(readOnly = true)
    public ToolDTO.GetToolDTO getToolById(Long id){
        var tool = toolRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Tool not found: " + id));
        return toDto(tool);
    }


    // UTILITIES
    public ToolDTO.GetToolDTO toDto(Tool tool){
        return new ToolDTO.GetToolDTO(
                tool.getId(),
                tool.getName(),
                tool.getPrice(),
                tool.getCallBackData(),
                tool.getStatus(),
                tool.getCreatedAt()
        );
    }

}
