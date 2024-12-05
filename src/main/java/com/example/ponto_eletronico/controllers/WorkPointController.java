package com.example.ponto_eletronico.controllers;

import com.example.ponto_eletronico.domain.user.User;
import com.example.ponto_eletronico.domain.workpoint.WorkPoint;
import com.example.ponto_eletronico.dto.WorkPointRequest;
import com.example.ponto_eletronico.infra.security.TokenService;
import com.example.ponto_eletronico.repository.UserRepository;
import com.example.ponto_eletronico.service.WorkPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/points")
public class WorkPointController {

    private final WorkPointService workPointService;
    private final UserRepository repository;
    private final TokenService tokenService;

    public WorkPointController(WorkPointService workPointService, UserRepository repository, TokenService tokenService) {
        this.workPointService = workPointService;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<WorkPoint>> getAllWorkPoints(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = tokenService.getEmailFromToken(token);

        List<WorkPoint> workPoints = workPointService.getWorkPointsByUserEmail(email);

        return ResponseEntity.ok(workPoints);
    }

    @PostMapping
    public ResponseEntity<WorkPoint> registerPoint(@RequestBody WorkPointRequest request,@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = tokenService.getEmailFromToken(token);

        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            WorkPoint point = workPointService.registerPoint(user.get(), request.getTimestamp());
            return ResponseEntity.status(HttpStatus.CREATED).body(point);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
