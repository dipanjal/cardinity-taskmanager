package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.model.request.user.UpdateUserRequest;
import com.cardinity.assessment.model.response.user.UserResponse;
import com.cardinity.assessment.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/user/get-all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(
                userService.findAllUsers()
        );
    }

    @GetMapping("/user/get-by-id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(
                userService.findUserById(id)
        );
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Valid CreateUserRequest dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                userService.createUser(dto)
        );
    }

    @PostMapping("/user/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                userService.updateUser(dto)
        );
    }

    @DeleteMapping("/user/delete-by-id/{id}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
