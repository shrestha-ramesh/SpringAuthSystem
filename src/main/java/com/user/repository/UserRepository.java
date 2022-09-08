package com.user.repository;

import com.user.model.user.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserRegister, String> {

    @Query("select u from UserRegister u where u.emailAddress = :emailAddress")
    UserRegister findByEmailAddress(@Param("emailAddress") String emailAddress);
    @Query("select u from UserRegister u where u.emailAddress = :emailAddress")
    UserRegister userLogIn(@Param("emailAddress") String emailAddress);

    @Modifying
    @Query("update UserRegister u set u.token = :token where u.emailAddress = :emailAddress")
    void saveToken(@Param("token") String token, @Param("emailAddress") String emailAddress);

}
