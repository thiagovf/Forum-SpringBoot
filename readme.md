# Curso de Spring Boot API Rest: Construa uma API

[Spring Initializr](https://start.spring.io/)
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