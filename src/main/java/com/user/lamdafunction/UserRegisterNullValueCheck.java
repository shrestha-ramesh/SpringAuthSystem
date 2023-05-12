package com.user.lamdafunction;

import com.user.model.user.UserRegister;

import java.util.function.Predicate;

public class BooleanValueCheck implements Predicate<UserRegister> {

    @Override
    public boolean test(UserRegister userRegister) {
        return userRegister == null ? true : false;
    }
}
