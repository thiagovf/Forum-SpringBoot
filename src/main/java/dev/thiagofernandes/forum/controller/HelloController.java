package dev.thiagofernandes.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@ResponseBody
	public String hello() {
		return "hello world";
	}
}
