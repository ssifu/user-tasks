package com.samiulsifat.task_management.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.samiulsifat.task_management.config.RoleSetConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@DynamoDBTable(tableName = "Users")
public class User implements UserDetails {
    @DynamoDBHashKey(attributeName = "username")
    private String username;

    @DynamoDBAttribute(attributeName = "password")
    private String password;

//    @DynamoDBAttribute(attributeName = "role")
//    private Role role;

    @DynamoDBAttribute(attributeName = "roles")
    @DynamoDBTypeConverted(converter = RoleSetConverter.class)
    private Set<Role> roles;

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    @DynamoDBIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @DynamoDBIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @DynamoDBIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @DynamoDBIgnore
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @DynamoDBIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Role getRole() {
//        return this.role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

// eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhbm5hc21pdGgiLCJpYXQiOjE3NDEwODEzMzQsImV4cCI6MTc0MzY3MzMzNH0.zyKrk17nosR6hI91K_ufilaUxll37kWhDlpJAgZIFusvLHusD3iGs6S6wB60uho1
// eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYW1pdWxzaWZhdCIsImlhdCI6MTc0MTA4MTM3NCwiZXhwIjoxNzQzNjczMzc0fQ.4OhTKt7GVP87sQAmRswnoHhqh2BXrXbLpJ3alKHfTCWIflBHJwi4_Ynjj6emaE4V