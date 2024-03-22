package org.shoppingMall.repository.user;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingMall.repository.userRoles.UserRoles;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="userId")
@Table(name= "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Integer userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name= "email", nullable = false)
    private String email;

    @Column(name= "nick_name", nullable = false)
    private String nickName;

    @Column(name= "password", nullable = false)
    private String password;

    @Column(name= "profile_img", nullable = false)
    private String profileImg;

    @Column(name= "address", nullable = false)
    private String address;

    @Column(name= "gender", nullable = false)
    private String gender;

    @Column(name= "status", nullable = false)
    private String status;

    @Column(name= "failiure_count", nullable = false)
    private Integer failiureCount;

    @Column(name= "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name= "delete_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(name= "lock_at", nullable = false)
    private LocalDateTime lockedAt;

    @OneToMany(mappedBy= "user")
    private List<UserRoles> userRoles;

}
