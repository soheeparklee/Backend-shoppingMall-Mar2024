package org.shoppingMall.repository.roles;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of= "rolesId")
@Table(name= "roles")

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "roles_id")
    private Integer rolesId;

    @Column(name= "name", nullable = false)
    private String name;
}
