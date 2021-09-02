package com.callteam.repository;

import com.callteam.entity.LoginEntity;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<LoginEntity,String> {

    LoginEntity findByEmailAndStatus(String email,String status);
}
