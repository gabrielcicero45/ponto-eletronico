package com.example.ponto_eletronico.repository;

import com.example.ponto_eletronico.domain.workpoint.WorkPoint;
import com.example.ponto_eletronico.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkPointRepository extends JpaRepository<WorkPoint, Long> {
    List<WorkPoint> findByUserAndTimestampBetween(User user, String start, String end);
    List<WorkPoint> findByUserEmail(String email);
}
