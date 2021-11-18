package dev.thiagofernandes.forum.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.thiagofernandes.forum.controller.dto.TopicoDTO;
import dev.thiagofernandes.forum.controller.form.TopicoForm;
import dev.thiagofernandes.forum.modelo.Topico;
import dev.thiagofernandes.forum.repository.CursoRepository;
import dev.thiagofernandes.forum.repository.TopicoRespository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoRespository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	@ResponseBody
	public List<TopicoDTO> lista(String cursoNome) {
		List<Topico> topicos;
		if (cursoNome == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.findByCursoNome(cursoNome);
		}
		
		return TopicoDTO.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
}
