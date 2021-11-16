package dev.thiagofernandes.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.thiagofernandes.forum.modelo.Topico;

public interface TopicoRespository extends JpaRepository<Topico, Long> {

	List<Topico> findByTitulo(String titulo);

	List<Topico> findByCursoNome(String cursoNome);
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
