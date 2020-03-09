package com.example.springboot.titan.user.repository;

import com.example.springboot.titan.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Eric.Mo
 * @since 2020/3/8
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
