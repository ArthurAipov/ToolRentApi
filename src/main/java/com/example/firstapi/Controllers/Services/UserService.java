package com.example.firstapi.Controllers.Services;

import com.example.firstapi.Controllers.dtos.UserDTO;
import com.example.firstapi.Models.User;
import com.example.firstapi.Repositories.RoleRepository;
import com.example.firstapi.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    // GET REQUESTS
    @Transactional(readOnly = true)
    public UserDTO.GetUserDTO getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found " + id));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO.GetUserDTO> getUsersPage(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<UserDTO.GetUserDTO> getUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO.GetUserDTO getUserByChatId(String chatId) {
        var user = userRepository.findUserByChatId(chatId).
                orElseThrow(() -> new IllegalArgumentException("User with "
                        + chatId + " not found"));
        return toDto(user);
    }

    // POST REQUESTS
    // CREATE REQUESTS
    @Transactional
    public UserDTO.GetUserDTO createNewUser(UserDTO.PostUserDTO userRecord) {

        if (userRepository.existsUserByChatId(userRecord.chatId()))
            throw new IllegalArgumentException("User with chatId " + userRecord.chatId() + " already exists");
        var role = roleRepository.findById(userRecord.roleId()).
                orElseThrow(() -> new IllegalArgumentException("Role not found: " + userRecord.roleId()));

        var newUser = new User();
        newUser.setAlias(userRecord.alias());
        newUser.setChatId(userRecord.chatId());
        newUser.setName(userRecord.name());
        newUser.setSurname(userRecord.surname());
        newUser.setRole(role);
        newUser = userRepository.save(newUser);

        return toDto(newUser);
    }

    // UPDATE REQUESTS
    @Transactional
    public UserDTO.GetUserDTO updateUser(Long id, UserDTO.UpdateUserDTO updateUserDTO) {
        var user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with " + id + " not found"));
        user.setName(updateUserDTO.name());
        user.setAlias(updateUserDTO.alias());
        user.setSurname(updateUserDTO.surname());
        user.setBlackList(updateUserDTO.blackList());
        user.setApprove(updateUserDTO.approve());
        user = userRepository.save(user);
        return toDto(user);
    }

    @Transactional
    public void updateUserApprove(Long id, boolean newStatus) {
        int updated = userRepository.updateApproveStatus(id, newStatus);
        if (updated == 0) {
            throw new IllegalArgumentException("User with " + id + " not found");
        }
    }

    @Transactional
    public void updateUserBlackList(Long id, boolean newBlackList) {
        int update = userRepository.updateBlackList(id, newBlackList);
        if (update == 0) {
            throw new IllegalArgumentException("User with " + id + " not found");
        }
    }

    @Transactional
    public void updateUserRole(Long userId, Long roleId) {
        var user = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User with " + userId + " not found"));
        var role = roleRepository.findById(roleId).
                orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserName(Long id, String name) {
        var user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with " + id + " not found"));
        user.setName(name);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserSurname(Long id, String surname) {
        var user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with " + id + " not found"));
        user.setSurname(surname);
        userRepository.save(user);
    }


    // DELETE REQUESTS
    @Transactional
    public void deleteUserById(Long id) {
        var user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User with " + id + " not found"));
        userRepository.delete(user);
    }

    @Transactional
    public void deleteByChatId(String chatId) {
        var delete = userRepository.deleteByChatId(chatId);
        if (delete == 0) {
            throw new IllegalArgumentException("User with " + chatId + " chatId not found");
        }
    }


    // UTILITIES
    private UserDTO.GetUserDTO toDto(User user) {
        return new UserDTO.GetUserDTO(
                user.getId(),
                user.getAlias(),
                user.getName(),
                user.getSurname(),
                user.getRole().getName(),
                user.getChatId(),
                user.getBlackList(),
                user.getApprove(),
                user.getCreatedAt());
    }

}
