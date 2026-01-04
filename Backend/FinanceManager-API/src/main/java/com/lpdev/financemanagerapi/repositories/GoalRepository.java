package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {



    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_goal g
        WHERE g.user_id = :userId 
   """)
    List<Goal> findAllGoalsByUserId(@Param("userId") Long userId);
}
