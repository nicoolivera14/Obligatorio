package com.tuorganizacion.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
