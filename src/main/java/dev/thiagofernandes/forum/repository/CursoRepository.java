package dev.thiagofernandes.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.thiagofernandes.forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso);

}
