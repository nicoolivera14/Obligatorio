package com.tuorganizacion.backend.repository;

import com.tuorganizacion.backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
