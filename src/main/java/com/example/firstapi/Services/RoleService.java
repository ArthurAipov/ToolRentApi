package com.example.firstapi.Services;

import com.example.firstapi.dtos.RoleDTO;
import com.example.firstapi.Models.Role;
import com.example.firstapi.Repositories.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<RoleDTO.GetRoleDTO> getRoles() {
        return roleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RoleDTO.GetRoleDTO getRoleById(Long id){
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
        return toDto(role);
    }

    @Transactional
    public RoleDTO.GetRoleDTO createRole(RoleDTO.PostRoleDTO newRole){
        Role role = new Role(newRole.name());
        role = roleRepository.save(role);
        return toDto(role);
    }

    @Transactional
    public RoleDTO.GetRoleDTO updateRole(Long id,RoleDTO.PostRoleDTO updateRoleDTO){
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
        role.setName(updateRoleDTO.name());
        role = roleRepository.save(role);
        return toDto(role);
    }

    @Transactional
    public void deleteRoleById(Long id){
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
        roleRepository.delete(role);

    }

    private RoleDTO.GetRoleDTO toDto(Role role) {
        return new RoleDTO.GetRoleDTO(role.getId(), role.getName());
    }
}
