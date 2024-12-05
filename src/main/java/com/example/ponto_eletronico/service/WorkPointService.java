package com.example.ponto_eletronico.service;

import com.example.ponto_eletronico.domain.user.User;
import com.example.ponto_eletronico.domain.workpoint.WorkPoint;
import com.example.ponto_eletronico.repository.WorkPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPointService {

    private final WorkPointRepository workPointRepository;

    public WorkPointService(WorkPointRepository workPointRepository) {
        this.workPointRepository = workPointRepository;
    }

    public WorkPoint registerPoint(User user, String timestamp) {
        WorkPoint point = new WorkPoint();
        point.setUser(user);
        point.setTimestamp(timestamp);
        return workPointRepository.save(point);
    }

    public List<WorkPoint> getWorkPointsByUserEmail(String email) {
        return workPointRepository.findByUserEmail(email);
    }
}