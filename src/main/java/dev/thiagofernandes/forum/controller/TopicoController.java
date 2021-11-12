package dev.thiagofernandes.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.thiagofernandes.forum.controller.dto.TopicoDTO;
import dev.thiagofernandes.forum.modelo.Topico;
import dev.thiagofernandes.forum.repository.TopicoRespository;

@RestController
public class TopicoController {

	@Autowired
	private TopicoRespository repository;
	
	@RequestMapping(value = "/topicos")
	@ResponseBody
	public List<TopicoDTO> lista() {
		List<Topico> topicos = repository.findAll();
		
		return TopicoDTO.converter(topicos);
	}
}
