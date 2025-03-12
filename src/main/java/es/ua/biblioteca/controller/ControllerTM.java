package es.ua.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.IBookService;

//nos proporciona las vistas Thymeleaf
@Controller
public class ControllerTM {
	
	@Autowired
    private IBookService bookService;
	
	@RequestMapping("/books")
	public String libros(Model modelo) {
		
		List<Book> books = bookService.findAll();

		modelo.addAttribute("books", books);
		return "biblioteca";
	}
	
	@RequestMapping("/")
	public String hola(Model modelo) {

		modelo.addAttribute("mensaje", "Biblioteca de Laura y Mónica");
		return "index";
	}
	
	@RequestMapping("/createBook")
	public String createBook(Model model) {
		model.addAttribute("book", new Book());
	    return "form";
	}
	
	@RequestMapping("/searchBook")
	public String searchBook(@RequestParam(value = "texto", required = false) String texto, Model model) {
		//Añadimos la lógica para traer resultados según el texto ingresado del libro
		List<Book> books;

		if (texto != null && !texto.trim().isEmpty()) {
			books = bookService.search(texto);
		} else {
			books = bookService.findAll(); // Si no hay texto, mostrar todos los libros
		}

		model.addAttribute("libros", books);
		model.addAttribute("texto", texto); // Mantener el valor en el campo de búsqueda
		return "searchForm";
	}
	
	@PostMapping("/createBook")
	public String createBook(@ModelAttribute Book book, Model model) {
		String result = bookService.create(book);
	    model.addAttribute("book", book);
	    model.addAttribute("result", result);
	    return "result";
	}
}
