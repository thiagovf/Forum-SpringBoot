package dev.thiagofernandes.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDTO> dtos = new ArrayList<>();
		
		List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
		fieldErros.forEach(e -> {
			String mensagem = "Erro no campo " + e.getField();
			ErroDeFormularioDTO dto = new ErroDeFormularioDTO(e.getField(), mensagem);
			dtos.add(dto);
		});
		return dtos;
	}
}
