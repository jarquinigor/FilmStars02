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

import pe.edu.upc.spring.model.CriticReview;
import pe.edu.upc.spring.service.ICriticService;
import pe.edu.upc.spring.service.IMovieService;
import pe.edu.upc.spring.service.ICriticReviewService;

@Controller
@RequestMapping("/textoCritica")
public class CriticReviewController {
	@Autowired
	private ICriticService cService;
	
	@Autowired
	private IMovieService mService;
	
	@Autowired
	private ICriticReviewService crService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("CriticReview") CriticReview objCriticReview, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "CriticReview";
		else {
			boolean flag = crService.save(objCriticReview);
			if(flag)
				return "redirect:/textoCritica/listar";
			else {
				model.addAttribute("mensaje", "Ocurrio un rochezaso, LUZ ROJA");
				return "redirect:/textoCritica/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<CriticReview> objCriticReview = crService.findById(id);
		if (objCriticReview == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/peliculaGenero/listar";
		}
		else {
			model.addAttribute("criticReviewbusqueda", new CriticReview());
			model.addAttribute("listCritics",cService.findAllSortAsc());
			model.addAttribute("listMovies",mService.findAllSortAsc());
			model.addAttribute("listCriticReviews", crService.findAllSortAsc());
			if(objCriticReview.isPresent())
				objCriticReview.ifPresent(o -> model.addAttribute("criticReview",o));
			
			return "listCriticReview";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				crService.delete(id);
				model.put("criticReview",new CriticReview()); //importante
				model.put("criticReviewbusqueda", new CriticReview()); //importante
				model.put("listCritics",cService.findAllSortAsc());
				model.put("listMovies",mService.findAllSortAsc());
				model.put("listCriticReviews", crService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listMovies", mService.findAll());
		}
		return "listCriticReview";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("criticReview",new CriticReview());
		model.put("criticReviewbusqueda", new CriticReview());
		model.put("listCritics",cService.findAllSortAsc());
		model.put("listMovies",mService.findAllSortAsc());
		model.put("listCriticReviews", crService.findAllSortAsc());
		return "listCriticReview";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("criticReviewbusqueda") CriticReview CriticReview) 
		throws ParseException
	{
		List<CriticReview>listCriticReviews;
		listCriticReviews = crService.findByCriticName(CriticReview.getCritic().getNameCritic());
		
		if(listCriticReviews.isEmpty()) {
			listCriticReviews = crService.findByMovieName(CriticReview.getCritic().getNameCritic());
		}
		model.put("criticReview", new CriticReview());
		model.put("listCritics",cService.findAllSortAsc());
		model.put("listMovies",mService.findAllSortAsc());
		model.put("listCriticReviews", listCriticReviews);
		
		return "listCriticReview";
	}
}
