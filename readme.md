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
