package com.user.repository;


import com.user.model.user.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLookUpRepository extends JpaRepository<UserRegister, String> {

    @Query("select u from UserRegister u where u.emailAddress = :emailAddress")
    UserRegister findByEmailAddress(@Param("emailAddress") String emailAddress);

//    @Query("select u from UserRegister u where u.emailAddress = :emailAddress")
//    UserRegister isEmailVerify(@Param("emailAddress") String isEmailVerify);

    @Modifying
    @Query("update UserRegister u set u.accessCode = :accessCode where u.emailAddress = :emailAddress")
    int saveAccessCode(@Param("accessCode") int accessCode, @Param("emailAddress") String emailAddress);

    @Query("select u from UserRegister u where u.emailAddress = :emailAddress and u.accessCode = :accessCode")
    UserRegister verifyAccessCode(@Param("emailAddress") String emailAddress, @Param("accessCode") int accessCode);

    @Modifying
    @Query("update UserRegister u set u.isEmailVerify = :isEmailVerify where u.emailAddress = :emailAddress")
    void saveIsVerify(@Param("isEmailVerify") boolean isEmailVerify, @Param("emailAddress") String emailAddress);

    @Modifying
    @Query("update UserRegister u set u.userPassword = :userPassword where u.emailAddress = :emailAddress")
    void forgetPassword(@Param("userPassword") String password, @Param("emailAddress") String emailAddress);
}
