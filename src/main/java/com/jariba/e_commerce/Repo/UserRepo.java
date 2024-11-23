package com.jariba.e_commerce.Repo;


import com.jariba.e_commerce.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    Boolean existsByEmailId(String emailId);

    Boolean existsByUserName(String userName);

    User findByUserName(String UserName);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.activeStatus=false where u.userName = :userName ")
    void setNotActiveStatusbyUserName(String userName);
}
