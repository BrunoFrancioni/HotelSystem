package com.hotelsystem.repository;

import com.hotelsystem.models.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancellationRepository extends JpaRepository<Cancellation,Long> {
}
