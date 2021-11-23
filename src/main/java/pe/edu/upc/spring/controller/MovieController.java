package pe.edu.upc.spring.controller;

import java.util.ArrayList;
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

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.model.MovieActor;
import pe.edu.upc.spring.model.MovieGenre;
import pe.edu.upc.spring.service.ICriticReviewService;
import pe.edu.upc.spring.service.IDirectorService;
import pe.edu.upc.spring.service.IGenreService; //Para listar géneros
import pe.edu.upc.spring.service.IMovieActorService;
import pe.edu.upc.spring.service.IMovieGenreService;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.IUserReviewService;

@Controller
@RequestMapping("/pelicula")
public class MovieController {
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private IDirectorService dService;
	
	//Para listar géneros y actores
	@Autowired
	private IGenreService gService;
	@Autowired
	private IMovieGenreService mgService;
	@Autowired
	private IMovieActorService maService;
	
	//Para mostrar el FilmstarsRating
	@Autowired
	private IUserReviewService urService;
	@Autowired
	private ICriticReviewService crService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("movie") Movie objMovie, BindingResult binRes, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()) {
			model.addAttribute("moviebusqueda", new Movie()); //importante
			model.addAttribute("listDirectors",dService.findAllSortIdAsc());
			model.addAttribute("listMovies", mService.findAllSortIdAsc());
			return "listMovie";
		}		
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
			model.addAttribute("listDirectors",dService.findAllSortIdAsc());
			model.addAttribute("listMovies",mService.findAllSortIdAsc());
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
				model.put("listDirectors",dService.findAllSortIdAsc());
				model.put("listMovies", mService.findAllSortIdAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("movie",new Movie()); //importante
			model.put("moviebusqueda", new Movie()); //importante
			model.put("listDirectors",dService.findAllSortIdAsc());
			model.put("listMovies", mService.findAllSortIdAsc());
		}
		return "listMovie";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listDirectors",dService.findAllSortIdAsc());
		model.put("listMovies", mService.findAllSortIdAsc());
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
		model.put("listDirectors",dService.findAllSortIdAsc());
		model.put("listMovies", listMovies);
		
		return "listMovie";
	}
	
	@RequestMapping("/verPeliculas") //USUARIO
	public String moviesUser(Model model) {
		model.addAttribute("listMovies", mService.findAllSortNameAsc());
		model.addAttribute("listGenres", gService.findAllSortNameAsc());
		return "moviesUser";
	}
	
	@RequestMapping("/verPelicula")  //USUARIO
	public String movieUser(Model model, @RequestParam(value="id") Integer id, @RequestParam(value="idUser") Integer idUser) {
		//Actors y Genres
		List<Actor>listActors = new ArrayList<Actor>();
		List<Genre>listGenres = new ArrayList<Genre>();
		
		List<MovieActor>listMovieActors = maService.findByMovieId(id);
		List<MovieGenre>listMovieGenres = mgService.findByMovieId(id);
		
		for (int i = 0; i < listMovieActors.size(); i++) {
			listActors.add(listMovieActors.get(i).getActor());
		}
		for (int i = 0; i < listMovieGenres.size(); i++) {
			listGenres.add(listMovieGenres.get(i).getGenre());
		}

		//Final
		Optional<Movie> objMovie = mService.findById(id);
		if(objMovie.isPresent())
			objMovie.ifPresent(o -> model.addAttribute("movie",o));
		
		model.addAttribute("listActors",listActors);
		model.addAttribute("listGenres",listGenres);
		model.addAttribute("listMovieActors",listMovieActors);
		//LISTAR REVIEWS 
		model.addAttribute("filmstarsRate",urService.findFilmstarsRate(id));
		model.addAttribute("userReviews",urService.findByMovieId(id).size());
		model.addAttribute("criticReviews",crService.findByMovieId(id).size());
		//
		
		if(urService.findByMovieUserId(id, idUser).isEmpty())
		{
			model.addAttribute("userReview","..");
		}
		else {
			model.addAttribute("userReview",urService.findByMovieUserId(id, idUser).get(0).getQuantityUserReview());
		}

		return "movieUser";
	}
}
