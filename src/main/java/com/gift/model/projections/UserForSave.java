package com.gift.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForSave {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
