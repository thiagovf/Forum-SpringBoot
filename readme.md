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
Se ao invés de usar o padrão ```findBy...``` seguindo a nomenclatura do Spring, o desenvolvedor quiser criar algo customizado, teria que ser feito algo semelhante ao código abaixo.  
```java  
public interface TopicoRespository extends JpaRepository<Topico, Long> {

	List<Topico> findByTitulo(String titulo);

	List<Topico> findByCursoNome(String cursoNome);
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(String nomeCurso);

}```  
