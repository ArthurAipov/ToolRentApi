package com.example.firstapi.Controllers.Services;

import com.example.firstapi.Controllers.dtos.ToolDTO;
import com.example.firstapi.Models.Tool;
import com.example.firstapi.Repositories.ToolRepository;
import com.example.firstapi.Utilities.ToolStatus;
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
    public List<ToolDTO.GetToolDTO> getToolsByStatus(String status){
        var toolStatus = ToolStatus.fromDb(status);
        if (toolStatus == null){
            throw new IllegalArgumentException("not supportable tool status");
        }
        return toolRepository.findAllByStatus(toolStatus).stream().map(this::toDto).toList();
    }

    @Transactional
    public ToolDTO.GetToolDTO createTool(ToolDTO.PostToolDTO postToolDTO) {
        var toolByCallBackData = toolRepository.findToolByCallBackData(postToolDTO.callbackData());
        if (toolByCallBackData.isPresent())
            throw new IllegalArgumentException("Tool with this callback data exists");
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
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolCallBackData(Long id, String newCallBackData) {
        var tool = getToolOrThrow(id);
        var callBackTool = toolRepository.findToolByCallBackData(newCallBackData);
        if (callBackTool.isPresent()){
            if (callBackTool.get().getId() == tool.getId()){
                throw new IllegalArgumentException("You are trying to set exactly the same callback data for this tool");
            }
            else {
                throw new IllegalArgumentException("This callback data is not avalible");
            }
        }
        tool.setCallBackData(newCallBackData);
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolName(Long id, String newName) {
        var tool = getToolOrThrow(id);
        tool.setName(newName);
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateToolStatus(Long id, ToolStatus newToolStatus) {
        var tool = getToolOrThrow(id);
        tool.setStatus(newToolStatus);
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public ToolDTO.GetToolDTO updateTool(Long id, ToolDTO.PostToolDTO newToolParams) {
        var tool = getToolOrThrow(id);
        tool.setNewParams(newToolParams);
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Transactional
    public void deleteToolById(Long id) {
        toolRepository.delete(getToolOrThrow(id));
    }

    @Transactional
    public void deleteToolByCallback(String callbackData) {
        var tool = toolRepository.findToolByCallBackData(callbackData).
                orElseThrow(() -> new IllegalArgumentException("Tool with " + callbackData + " not found"));
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
        return toolRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Tool with " + id + " not found"));
    }

}
