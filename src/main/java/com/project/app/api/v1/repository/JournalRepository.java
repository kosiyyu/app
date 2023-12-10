package com.project.app.api.v1.repository;

import com.project.app.api.v1.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Integer> {

}


