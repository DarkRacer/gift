package com.gift.service;

import com.gift.model.entities.Role;
import com.gift.model.entities.Users;
import com.gift.model.projections.UserAndRole;
import com.gift.repository.RoleRepository;
import com.gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UsersService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Users getInfo (Long id) {
        return userRepository.findUsersById(id);
    }

    @Transactional
    public List<UserAndRole> getAll() {
        List<Users> users = userRepository.findAll();
        List<UserAndRole> userAndRoles = new ArrayList<>();
        List<String> roles = new ArrayList<>();

        for (Users user : users) {
            for (Role role : user.getRoles()){
                roles.add(role.getName());
            }
            userAndRoles.add(new UserAndRole(user.getId(), user.getPicture(), user.getFirstName() + " " + user.getLastName(), roles));
            roles = new ArrayList<>();
        }

        return userAndRoles;
    }

    @Transactional
    public void saveAdmin(UserAndRole userAndRole) {
        Users user = userRepository.findUsersById(userAndRole.getId());
        user.getRoles().add(roleRepository.findByName(Role.ROLE_ADMIN));
        userRepository.save(user);
    }

    @Modifying
    public void deleteAdmin(UserAndRole userAndRole) {
        Users user = userRepository.findUsersById(userAndRole.getId());
        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
