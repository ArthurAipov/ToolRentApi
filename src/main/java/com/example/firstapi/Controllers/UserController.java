package com.example.firstapi.Controllers;

import com.example.firstapi.Services.UserService;
import com.example.firstapi.dtos.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO.GetUserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO.GetUserDTO getUserById(@PathVariable @Positive Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/chatid/{chatId}")
    public UserDTO.GetUserDTO getUserByChatId(@PathVariable String chatId) {
        return userService.getUserByChatId(chatId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO.GetUserDTO createNewUser(@Valid @RequestBody UserDTO.PostUserDTO newUser) {
        return userService.createNewUser(newUser);
    }

    @PutMapping("/{id}/blacklist")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserBlackList(@PathVariable @Positive Long id,
                                    @RequestParam boolean newBlackList) {
        userService.updateUserBlackList(id, newBlackList);
    }

    @PutMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserApproveStatus(@PathVariable @Positive Long id,
                                        @RequestParam boolean newApproveStatus) {
        userService.updateUserApprove(id, newApproveStatus);
    }

    @PutMapping("/{id}")
    public UserDTO.GetUserDTO updateUser(@PathVariable @Positive Long id,
                                         @RequestBody @Valid UserDTO.UpdateUserDTO updateUserDTO) {
        return userService.updateUser(id, updateUserDTO);
    }

    @PutMapping("/{userId}/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserRole(@PathVariable @Positive Long userId,
                               @Positive @RequestParam Long roleId) {
        userService.updateUserRole(userId, roleId);
    }

    @PutMapping("/{id}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserName(@PathVariable @Positive Long id,
                               @RequestParam @NotBlank String name) {
        userService.updateUserName(id, name);
    }

    @PutMapping("/{id}/surname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserSurname(@PathVariable @Positive Long id,
                                  @RequestParam @NotBlank String surname) {
        userService.updateUserSurname(id, surname);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Positive Long id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping("/chatid/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByChatId(@PathVariable @NotBlank String chatId) {
        userService.deleteByChatId(chatId);
    }


}
