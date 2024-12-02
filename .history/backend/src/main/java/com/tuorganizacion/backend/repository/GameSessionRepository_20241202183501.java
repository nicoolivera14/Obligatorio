package com.tuorganizacion.backend.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuorganizacion.backend.model.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
  Optional<GameSession> findBySessionKey(String sessionKey);
  List<GameSession> findByStatus(String status);

  @Query("SELECT gs FROM GameSession gs JOIN gs.players u WHERE u.id = :userId AND gs.status = 'IN_PROGRESS'")
  List<GameSession> findActiveSessionsByUserId(@Param("userId") Long userId);

  @Query("SELECT gs FROM GameSession gs WHERE gs.status = :status AND gs.updatedAt < :cutoffTime")
  List<GameSession> findByStatusAndUpdatedAtBefore(@Param("status") String status, @Param("cutoffTime") Instant cutoffTime);
}
