package com.user.repo;

import com.user.model.user.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2Repository extends JpaRepository<UserRegister, String> {
}
