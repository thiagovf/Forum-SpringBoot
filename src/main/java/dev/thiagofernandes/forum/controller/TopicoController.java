package dev.thiagofernandes.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.thiagofernandes.forum.modelo.Curso;
import dev.thiagofernandes.forum.modelo.Topico;

@Controller
public class TopicoController {

	@RequestMapping(value = "/topicos")
	@ResponseBody
	public List<Topico> lista() {
		Topico topico = new Topico("Titulo", "Mensagem", new Curso("SpringBoot", "Programação"));
		
		return Arrays.asList(topico, topico, topico);
	}
}
