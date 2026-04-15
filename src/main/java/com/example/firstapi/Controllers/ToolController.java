package com.example.firstapi.Controllers;

import com.example.firstapi.Services.ToolService;
import com.example.firstapi.Utilities.ToolStatus;
import com.example.firstapi.dtos.ToolDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tools")
public class ToolController {

    private final ToolService toolService;
    @GetMapping
    public Page<ToolDTO.GetToolDTO> getTools(Pageable pageable) {
        return toolService.getTools(pageable);
    }

    @GetMapping("/{id}")
    public ToolDTO.GetToolDTO getTool(@PathVariable @Positive Long id) {
        return toolService.getToolById(id);
    }

    @GetMapping("/callback/{callback}")
    public ToolDTO.GetToolDTO getToolByCallbackData(@PathVariable @NotBlank String callback) {
        return toolService.getToolByCallBackData(callback);
    }

    @GetMapping("/all")
    public List<ToolDTO.GetToolDTO> getTools() {
        return toolService.getTools();
    }

    @GetMapping("/status/{status}/all")
    public List<ToolDTO.GetToolDTO> getToolsByStatus(@PathVariable @NotBlank String status) {
        return toolService.getToolsByStatus(status);
    }

    @GetMapping("/status/{status}")
    public Page<ToolDTO.GetToolDTO> getToolsByStatus(@PathVariable @NotBlank String status,
                                                     Pageable pageable) {
        return toolService.getToolsByStatus(status, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToolDTO.GetToolDTO createTool(@RequestBody @Valid ToolDTO.PostToolDTO newTool) {
        return toolService.createTool(newTool);
    }

    @PutMapping("/{id}/price")
    public ToolDTO.GetToolDTO updatePrice(@PathVariable @Positive Long id,
                                          @RequestBody  @Positive Long newPrice) {
        return toolService.updateToolPrice(id, newPrice);
    }

    @PutMapping("/{id}/callback")
    public ToolDTO.GetToolDTO updateCallbackData(@PathVariable @Positive Long id,
                                                 @RequestBody @NotBlank String newCallbackData) {
        return toolService.updateToolCallBackData(id, newCallbackData);
    }

    @PutMapping("/{id}/name")
    public ToolDTO.GetToolDTO updateName(@PathVariable @Positive Long id,
                                         @RequestBody @NotBlank String name) {
        return toolService.updateToolName(id, name);
    }

    @PutMapping("/{id}/status")
    public ToolDTO.GetToolDTO updateStatus(@PathVariable @Positive Long id,
                                           @RequestBody @NotBlank String newStatus) {
        var status = ToolStatus.fromDb(newStatus);
        if (status == null) {
            throw new IllegalArgumentException("Unsupported tool status");
        }
        return toolService.updateToolStatus(id, newStatus);
    }

    @PutMapping("/{id}")
    public ToolDTO.GetToolDTO updateTool(@PathVariable @Positive Long id,
                                         @RequestBody @Valid ToolDTO.PostToolDTO newToolParams) {
        return toolService.updateTool(id, newToolParams);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive Long id) {
        toolService.deleteToolById(id);
    }

    @DeleteMapping("/callback/{callback}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCallback(@PathVariable @NotBlank String callback) {
        toolService.deleteToolByCallBack(callback);
    }

}
