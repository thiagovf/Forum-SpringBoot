# Curso de Spring Boot API Rest: Construa uma API

[Spring Initializr](https://start.spring.io/)  
## Hot Deploy  
Para que não seja necessário ficar parando e iniciando o servidor a cada mudança feita no código, o Spring Boot já fornece uma biblioteca que faz o hot deploy automático. Para usá-la, é necessário adicionar no pom.xml a dependência abaixo.  
```xml  
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
</dependency>
```   

## Hello World  
Em um método do controller, se não tiver a anotação ```@ResponseBody```, o método irá procurar uma URL da string retornada.  
```java  
@Controller
public class HelloController {  
	@RequestMapping(value = "/hello")
	@ResponseBody  
	public String hello() {  
		return "hello world";  
	}  
}  
```  
## ```@RestController``` ao invés de ```@Controller```  
Para que não precisemos ficar adicionando ```@ResponseBody``` em todos métodos que queiramos que se comporte como um serviço rest, podemos substituir o ```@Controller``` por ```@RestController```. Com isso, o Spring Boot já entenderá que os métodos devem ser tratados como um retorno e não um redirecionamento de página.  
```java  
@RestController
public class TopicoController {

	@RequestMapping(value = "/topicos")
	@ResponseBody
	public List<Topico> lista() {
		Topico topico = new Topico("Titulo", "Mensagem", new Curso("SpringBoot", "Programação"));
		
		return Arrays.asList(topico, topico, topico);
	}
}   
```  
## Spring Data JpaRepository  
O Spring possibilita que definamos apenas uma interface para que ele crie as JPQL automaticamente, abstraindo do desenvolvedor essa complexidade.  
```java  
public interface TopicoRespository extends JpaRepository<Topico, Long> {

	List<Topico> findByTitulo(String titulo);

	List<Topico> findByCursoNome(String cursoNome);

}    
```  
Detalhe importante é a forma como podemos "customizar" as queries. No exemplo acima, o ```findByTitulo``` vai fazer uma query usando o atributo do ```Titulo``` da entidade ```Topico```. No caso do médoto ```findByCursoNome```, ```Topico``` possui um relacionamento com a entidade Curso e esta possui o atributo ```Nome```. Dessa forma, o Spring Data consegue fazer a tradução correta.  
Outro ponto que deve ser observado é no seguinte cenário: supondo que na entidade ```Topico``` tivéssemos o atributo ```CursoNome```. Ao criar o método ```findByCursoNome``` com o objetivo de pegar o ```Nome``` da entidade ```Curso```, ocorreria uma ambiguidade onde o seria retornado o valor do atributo ```CursoNome```. Para resolver isso, deveríamos criar o método ```findByCurso_Nome```.  
### Customizando o nome do método  
Se ao invés de usar o padrão ```findBy...``` seguindo a nomenclatura do Spring, o desenvolvedor quiser criar algo customizado, teria que ser feito algo semelhante ao código abaixo que usa ```org.springframework.data.jpa.repository.Query```.  
```java  
public interface TopicoRespository extends JpaRepository<Topico, Long> {

	List<Topico> findByTitulo(String titulo);

	List<Topico> findByCursoNome(String cursoNome);
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
```  
## Bean Validation  
Para validarmos as informações que são passadas nas requisições, poderíamos adicionar if e else no próprio controller onde elas chegam. No entanto, ao fazer isso, iríamos ter que fazer vários ifs e elses, além de meio que sobrecarregar o controller com atividades que não são propriamente sua.  
Ao invés disso, adicionamos a dependência ```javax.validation``` ao projeto e colocamos anotações como a exposta abaixo no DTO ```@NotNull```, ```@NotEmpty``` e ```@Sizeque``` é recebido e no parâmetro do método do controller que recebe a requisição ```@Valid```.  
[Link para o commit dessa alteração](https://github.com/thiagovf/Forum-SpringBoot/commit/f234e4ad5ef9c02ab2842c8df2f8de5070776b26)  
```java  
public class TopicoForm {  

	@NotNull  
	@NotEmpty  
	@Size(min = 5)  
	private String titulo;  
	
	@NotNull  
	@NotEmpty  
	@Size(min = 10)  
	private String mensagem;  
	 
	@NotNull  
	@NotEmpty  
	private String nomeCurso;   
}  
```  
```java  
@PostMapping
public ResponseEntity<TopicoDTO> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
	Topico topico = form.converter(cursoRepository);
	topicoRepository.save(topico);

	URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
	return ResponseEntity.created(uri).body(new TopicoDTO(topico));
}  
```  
## Tratamento de exceção  
O Spring Boot já tem um tratamento de exceção padrão. No entanto, ele retorna uma quantidade enorme de informações que pode não ser de interesse do desenvolvedor. Para isso, podemos criar um ```ExceptionHandler```. Dessa forma, podemos tratar o erro e retornar só as informações que julgarmos úteis.  
```java  
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
```  
### ```@RestControllerAdvice```  
O Spring Boot possui o conceito de *Controller Advice* que permite tratar o erro que ocorrem em todos os controllers da aplicação. No entanto, como estamos trabalhando com serviços rest, existe a anotação ```@RestControllerAdvice``` que irá capturar as exceptions do serviço. 
[Link para mais detalhes](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc)
### ```@ExceptionHandler```  
Vai receber como parâmetro o tipo de exception que pretendemos capturar naquele método específico. No caso do código, capturamos o ```MethodArgumentNotValidException```.  
### ```@ResponseStatus```  
Já o Response Status é para evitar que o serviço retorne o status HTTP 200, indicativo de que tudo ocorreu bem. Como fizemos o tratamento, o retorno default seria dessa família. Da forma apresentada no código, ```code = HttpStatus.BAD_REQUEST``` será retornado um da família 400, indicando que houve um erro.  

