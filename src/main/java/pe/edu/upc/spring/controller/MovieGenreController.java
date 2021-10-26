package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import pe.edu.upc.spring.model.MovieGenre;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IGenreService;
import pe.edu.upc.spring.service.IMovieGenreService;

@Controller
@RequestMapping("/peliculaGenero")
public class MovieGenreController {
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private IGenreService gService;
	
	@Autowired
	private IMovieGenreService mgService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("movieGenre") MovieGenre objMovieGenre, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "movieGenre";
		else {
			boolean flag = mgService.save(objMovieGenre);
			if(flag)
				return "redirect:/peliculaGenero/listar";
			else {
				model.addAttribute("mensaje", "Ocurrio un rochezaso, LUZ ROJA");
				return "redirect:/peliculaGenero/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<MovieGenre> objMovieGenre = mgService.findById(id);
		if (objMovieGenre == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/peliculaGenero/listar";
		}
		else {
			model.addAttribute("movieGenrebusqueda", new MovieGenre());
			model.addAttribute("listMovies",mService.findAllSortAsc());
			model.addAttribute("listGenres",gService.findAllSortAsc());
			model.addAttribute("listMovieGenres", mgService.findAllSortAsc());
			if(objMovieGenre.isPresent())
				objMovieGenre.ifPresent(o -> model.addAttribute("movieGenre",o));
			
			return "listMovieGenre";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				mgService.delete(id);
				model.put("movieGenre",new MovieGenre()); //importante
				model.put("movieGenrebusqueda", new MovieGenre()); //importante
				model.put("listMovies",mService.findAllSortAsc());
				model.put("listGenres",gService.findAllSortAsc());
				model.put("listMovieGenres", mgService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listMovies", mService.findAll());
		}
		return "listMovieGenre";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("movieGenre",new MovieGenre());
		model.put("movieGenrebusqueda", new MovieGenre());
		model.put("listMovies",mService.findAllSortAsc());
		model.put("listGenres",gService.findAllSortAsc());
		model.put("listMovieGenres", mgService.findAllSortAsc());
		return "listMovieGenre";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("movieGenrebusqueda") MovieGenre movieGenre) 
		throws ParseException
	{
		List<MovieGenre>listMovieGenres;
		listMovieGenres = mgService.findByMovieName(movieGenre.getMovie().getNameMovie());
		
		if(listMovieGenres.isEmpty()) {
			listMovieGenres = mgService.findByGenreName(movieGenre.getMovie().getNameMovie());
		}
		model.put("movieGenre", new MovieGenre());
		model.put("listMovies",mService.findAllSortAsc());
		model.put("listGenres",gService.findAllSortAsc());
		model.put("listMovieGenres", listMovieGenres);
		
		return "listMovieGenre";
	}
}
