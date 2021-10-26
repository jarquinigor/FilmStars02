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

import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.service.IDirectorService;
import pe.edu.upc.spring.service.IMovieService;

@Controller
@RequestMapping("/pelicula")
public class MovieController {
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private IDirectorService dService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("movie") Movie objMovie, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "movie";
		else {
			boolean flag = mService.save(objMovie);
			if(flag)
				return "redirect:/pelicula/listar";
			else {
				model.addAttribute("mensaje", "Ocurrio un rochezaso, LUZ ROJA");
				return "redirect:/pelicula/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Movie> objMovie = mService.findById(id);
		if (objMovie == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/pelicula/listar";
		}
		else {
			model.addAttribute("moviebusqueda", new Movie());
			model.addAttribute("listDirectors",dService.findAllSortAsc());
			model.addAttribute("listMovies",mService.findAllSortAsc());
			if(objMovie.isPresent())
				objMovie.ifPresent(o -> model.addAttribute("movie",o));
			
			return "listMovie";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				mService.delete(id);
				model.put("movie",new Movie()); //importante
				model.put("moviebusqueda", new Movie()); //importante
				model.put("listDirectors",dService.findAllSortAsc());
				model.put("listMovies", mService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listMovies", mService.findAll());
		}
		return "listMovie";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listDirectors",dService.findAllSortAsc());
		model.put("listMovies", mService.findAllSortAsc());
		model.put("movie",new Movie());
		model.put("moviebusqueda", new Movie()); 
		return "listMovie";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("moviebusqueda") Movie movie) 
		throws ParseException
	{
		List<Movie>listMovies;
		listMovies = mService.findByName(movie.getNameMovie());
		
		model.put("movie", new Movie());
		model.put("listDirectors",dService.findAllSortAsc());
		model.put("listMovies", listMovies);
		
		return "listMovie";
	}
}
