package com.springboot_multithreading_example.springboot_multithreading_example.repository;

import com.springboot_multithreading_example.springboot_multithreading_example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
