package org.shoppingMall.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpa extends JpaRepository<User, Integer> {
    @Query(
            "SELECT u " +
                    "FROM User u " +
                    "JOIN FETCH u.userRoles ur " +
                    "JOIN FETCH ur.roles r " +
                    "WHERE u.email = ?1 "
    )


    Optional<User> findByEmailFetchJoin(String email);

    boolean existsByEmail(String email);
}
