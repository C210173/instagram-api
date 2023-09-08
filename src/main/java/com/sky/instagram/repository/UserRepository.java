package com.sky.instagram.repository;

import com.sky.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    List<User> findAllUsersByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:query% OR u.email LIKE %:query% OR u.name LIKE %:query%")
    List<User> findByQuery(@Param("query") String query);

    // Thêm phương thức lấy những người dùng có nhiều người theo dõi
    @Query("SELECT u FROM User u ORDER BY SIZE(u.follower) DESC")
    List<User> findPopularUsers();
}
