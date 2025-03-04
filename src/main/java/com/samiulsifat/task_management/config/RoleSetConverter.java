package com.samiulsifat.task_management.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.samiulsifat.task_management.model.Role;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleSetConverter implements DynamoDBTypeConverter<String, Set<Role>> {

    @Override
    public String convert(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Role> unconvert(String s) {
        if (s == null || s.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(s.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}

