package com.gift.service;

import com.gift.model.entities.Role;
import com.gift.model.entities.Users;
import com.gift.model.projections.UserAndRole;
import com.gift.model.projections.UserForSave;
import com.gift.repository.RoleRepository;
import com.gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
    public List<UserAndRole> getAll(Long id) {
        List<Users> users = userRepository.findAll();
        users.removeIf(user -> Objects.equals(user.getId(), id));
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

    @Transactional
    public List<String> getRoles(Long id) {
        Users users = userRepository.findUsersById(id);
        List<String> roles = new ArrayList<>();

        for (Role role : users.getRoles()){
            roles.add(role.getName());
        }

        return roles;
    }

    @Transactional
    public void saveUser(UserForSave userForSave) {
        Users users = userRepository.findUsersById(userForSave.getId());

        users.setFirstName(userForSave.getFirstName());
        users.setLastName(userForSave.getLastName());
        users.setEmail(userForSave.getEmail());

        userRepository.save(users);
    }
}
