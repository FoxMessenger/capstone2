package com.trilogyed.repository;

import com.trilogyed.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Integer> {


}