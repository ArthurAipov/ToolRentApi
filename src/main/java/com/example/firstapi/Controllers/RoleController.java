package com.example.firstapi.Controllers;

import com.example.firstapi.Services.RoleService;
import com.example.firstapi.dtos.RoleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDTO.GetRoleDTO> getRoles(){
        return roleService.getRoles();
    }

    @GetMapping("/{id}")
    public RoleDTO.GetRoleDTO getRole(@PathVariable @Positive Long id){
        return roleService.getRoleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO.GetRoleDTO createRole(@Valid @RequestBody RoleDTO.PostRoleDTO newRole){
        return roleService.createRole(newRole);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable @Positive Long id){
        roleService.deleteRoleById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus
    public RoleDTO.GetRoleDTO updateRole(@PathVariable @Positive Long id,
                                         @Valid @RequestBody RoleDTO.PostRoleDTO updateRoleDTO){
        return roleService.updateRole(id, updateRoleDTO);
    }

}
