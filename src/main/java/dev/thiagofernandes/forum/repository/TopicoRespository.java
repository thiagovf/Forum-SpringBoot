package dev.thiagofernandes.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.thiagofernandes.forum.modelo.Topico;

public interface TopicoRespository extends JpaRepository<Topico, Long> {

}
