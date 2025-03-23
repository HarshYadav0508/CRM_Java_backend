package crm.channi.demo.service;

import crm.channi.demo.model.User;
import crm.channi.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) {
        return userRepository.findByUsername(username).filter(user -> passwordEncoder.matches(password, user.getPassword())).orElse(null);
    }

    public String createUser(String adminUsername, String username, String password) {

        User admin = userRepository.findByUsername(adminUsername).orElse(null);

        if (admin == null || !admin.getRole().equals("admin")) {
            return "Access Denied: Only Admin can create users!";
        }

        if(userRepository.findByUsername(username).isPresent()) {
            return "Error: User already exists!";
        }

        User newUser = new User(username, passwordEncoder.encode(password), "user");
        userRepository.save(newUser);
        return "User created successfully!";

    }
}
