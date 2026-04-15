package com.example.firstapi.Services;

import com.example.firstapi.dtos.ToolDTO;
import com.example.firstapi.Models.Tool;
import com.example.firstapi.Repositories.ToolRepository;
import com.example.firstapi.Utilities.ToolStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToolService {
    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    // GET REQUESTS
    @Transactional(readOnly = true)
    public ToolDTO.GetToolDTO getToolById(Long id) {
        return toDto(getToolOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<ToolDTO.GetToolDTO> getTools() {
        return toolRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ToolDTO.GetToolDTO> getToolsByStatus(String status) {
        var toolStatus = ToolStatus.fromDb(status);
        if (toolStatus == null) {
            throw new IllegalArgumentException("Unsupported tool status: " + status);
        }
        return toolRepository.findAllByStatus(toolStatus).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ToolDTO.GetToolDTO getToolByCallBackData(String callBackData) {
        var tool = getToolByCallBackOrThrow(callBackData);
        return toDto(tool);
    }

    @Transactional(readOnly = true)
    public Page<ToolDTO.GetToolDTO> getTools(Pageable pageable) {
        return toolRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ToolDTO.GetToolDTO> getToolsByStatus(String status, Pageable pageable) {
        var toolStatus = ToolStatus.fromDb(status);
        if (toolStatus == null) {
            throw new IllegalArgumentException("Unsupported tool status: " + status);
        }
        return toolRepository.findAllByStatus(toolStatus, pageable).map(this::toDto);
    }

    @Transactional
    public ToolDTO.GetToolDTO createTool(ToolDTO.PostToolDTO postToolDTO) {
        if (toolRepository.existsByCallBackData(postToolDTO.callbackData()))
            throw new IllegalArgumentException("Tool with callback data '"
                    + postToolDTO.callbackData() + "' already exists");
        var tool = new Tool();
        tool.setName(postToolDTO.name());
        tool.setPrice(postToolDTO.price());
        tool.setCallBackData(postToolDTO.callbackData());
        tool.setStatus(postToolDTO.toolStatus());
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolPrice(Long id, Long newPrice) {
        var tool = getToolOrThrow(id);
        tool.setPrice(newPrice);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolCallBackData(Long id, String newCallBackData) {
        var tool = getToolOrThrow(id);

        if (toolRepository.existsByCallBackDataAndIdNot(newCallBackData, id)) {
            throw new IllegalArgumentException("Tool with callback data '"
                    + newCallBackData + "' already exists");
        }

        tool.setCallBackData(newCallBackData);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolName(Long id, String newName) {
        var tool = getToolOrThrow(id);
        tool.setName(newName);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolStatus(Long id, String newToolStatus) {
        var toolStatus = ToolStatus.fromDb(newToolStatus);
        if (toolStatus == null) {
            throw new IllegalArgumentException("Unsupported tool status: "
                    + newToolStatus);
        }
        var tool = getToolOrThrow(id);
        tool.setStatus(toolStatus);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateTool(Long id, ToolDTO.PostToolDTO newToolParams) {
        if (toolRepository.existsByCallBackDataAndIdNot(newToolParams.callbackData(), id))
            throw new IllegalArgumentException("Tool with callback data '"
                    + newToolParams.callbackData() + "' already exists");
        var tool = getToolOrThrow(id);
        tool.setName(newToolParams.name());
        tool.setPrice(newToolParams.price());
        tool.setCallBackData(newToolParams.callbackData());
        tool.setStatus(newToolParams.toolStatus());
        return toDto(tool);
    }

    @Transactional
    public void deleteToolById(Long id) {
        var tool = getToolOrThrow(id);
        toolRepository.delete(tool);
    }

    @Transactional
    public void deleteToolByCallBack(String callbackData) {
        var tool = getToolByCallBackOrThrow(callbackData);
        toolRepository.delete(tool);
    }

    // UTILITIES
    private ToolDTO.GetToolDTO toDto(Tool tool) {
        return new ToolDTO.GetToolDTO(
                tool.getId(),
                tool.getName(),
                tool.getPrice(),
                tool.getCallBackData(),
                tool.getStatus(),
                tool.getCreatedAt()
        );
    }

    private Tool getToolOrThrow(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tool with id '"
                        + id + "' not found"));
    }

    private Tool getToolByCallBackOrThrow(String callBackData) {
        return toolRepository.findToolByCallBackData(callBackData)
                .orElseThrow(() -> new IllegalArgumentException("Tool with callback data '"
                        + callBackData + "' not found"));
    }

}
