package crm.channi.demo.controller;

import crm.channi.demo.model.User;
import crm.channi.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userApi")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.authenticate(username, password);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
        return ResponseEntity.ok("Login successful. Welcome " + user.getUsername() + "!");
    }

    @PostMapping("/admin/createUser")
    public ResponseEntity<String> createUser (
            @RequestParam String adminUsername,
            @RequestParam String username,
            @RequestParam String password
    ) {
        String response = userService.createUser(adminUsername, username, password);
        return ResponseEntity.ok(response);
    }
}
