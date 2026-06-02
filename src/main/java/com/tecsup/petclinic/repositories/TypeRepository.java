package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    List<Type> findByActiveTrue();

    @Query("""
           SELECT t.name, COUNT(p.id)
           FROM types t LEFT JOIN pets p ON p.typeId = t.id
           WHERE t.active = true
           GROUP BY t.id, t.name
           ORDER BY t.id
           """)
    List<Object[]> getPetCountByType();
}
