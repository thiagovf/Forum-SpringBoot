package dev.thiagofernandes.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.thiagofernandes.forum.controller.dto.TopicoDTO;
import dev.thiagofernandes.forum.modelo.Curso;
import dev.thiagofernandes.forum.modelo.Topico;

@RestController
public class TopicoController {

	@RequestMapping(value = "/topicos")
	@ResponseBody
	public List<TopicoDTO> lista() {
		Topico topico = new Topico("Titulo", "Mensagem", new Curso("SpringBoot", "Programação"));
		
		return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
	}
}
