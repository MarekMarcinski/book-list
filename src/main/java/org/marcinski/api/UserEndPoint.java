package org.marcinski.api;

import org.marcinski.model.Role;
import org.marcinski.model.User;
import org.marcinski.repository.RoleRepository;
import org.marcinski.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
public class UserEndPoint {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEndPoint(UserRepository userRepository,
                        RoleRepository roleRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("api/users")
    public ResponseEntity<?> save(@RequestBody User user){
        if (user.getId() == null){

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            Role userRole = roleRepository.findByRole("USER");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            User userToSave = userRepository.save(user);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}/")
                    .buildAndExpand(userToSave.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(user);
        }else {
         return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
