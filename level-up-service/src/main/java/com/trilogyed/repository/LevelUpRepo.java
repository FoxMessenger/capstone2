package com.trilogyed.repository;

import com.trilogyed.model.LevelUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelUpRepo extends JpaRepository<LevelUp, Integer> {

    LevelUp findByCustomerId(int customerId);
}