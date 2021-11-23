package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.service.IGenreService;

@Controller
@RequestMapping("/genero")
public class GenreController {
	@Autowired
	private IGenreService gService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("genre") Genre objGenre, BindingResult binRes, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("listGenres", gService.findAllSortIdAsc());
			model.addAttribute("genrebusqueda", new Genre());
			return "listGenre";
		}
		else {
			int rpta = gService.save(objGenre);
			if(rpta > 0) {
				model.addAttribute("mensaje", "Ya existe este género");
				model.addAttribute("listGenres", gService.findAllSortIdAsc());
				model.addAttribute("genre",new Genre());
				model.addAttribute("genrebusqueda", new Genre());
				return "listGenre";
			}	
			else {
				model.addAttribute("mensaje", "Se registro un género correctamente");
				model.addAttribute("listGenres", gService.findAllSortIdAsc());
				model.addAttribute("genre",new Genre());
				model.addAttribute("genrebusqueda", new Genre());
				return "listGenre";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Genre> objGenre = gService.findById(id);
		if (objGenre == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/genero/listar";
		}
		else {
			model.addAttribute("genre", objGenre);
			model.addAttribute("genrebusqueda", new Genre());
			model.addAttribute("listGenres", gService.findAllSortNameAsc());
			return "listGenre";
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				gService.delete(id);
				model.put("genre", new Genre());
				model.put("genrebusqueda", new Genre());
				model.put("listGenres", gService.findAllSortNameAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("genre", new Genre());
			model.put("genrebusqueda", new Genre());
			model.put("listGenres", gService.findAllSortNameAsc());
		}
		
		return "listGenre";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listGenres", gService.findAllSortNameAsc());
		model.put("genre",new Genre());
		model.put("genrebusqueda", new Genre());
		
		return "listGenre";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("genrebusqueda") Genre genre) 
		throws ParseException
	{
		List<Genre>listGenres;
		listGenres = gService.findByName(genre.getNameGenre());
		
		model.put("genre", new Genre());
		model.put("listGenres", listGenres);
		
		return "listGenre";
	}
}
