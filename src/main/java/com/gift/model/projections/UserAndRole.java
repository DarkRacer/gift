package com.gift.model.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAndRole {
    private Long id;
    private String picture;
    private String name;
    private List<String> role;
}
