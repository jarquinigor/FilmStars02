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

import pe.edu.upc.spring.model.Director;
import pe.edu.upc.spring.service.IDirectorService;

@Controller
@RequestMapping("/director")
public class DirectorController {
	@Autowired
	private IDirectorService dService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("director") Director objDirector, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "director";
		else {
			boolean flag = dService.save(objDirector);
			if(flag)
				return "redirect:/director/listar";
			else {
				model.addAttribute("mensaje", "Rochezaso");
				return "redirect:/director/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Director> objDirector = dService.findById(id);
		if (objDirector == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/director/listar";
		}
		else {
			model.addAttribute("director", objDirector);
			model.addAttribute("directorbusqueda", new Director());
			model.addAttribute("listDirectos", dService.findAllSortAsc());
			return "listDirector";
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				dService.delete(id);
				model.put("director",new Director());
				model.put("directorbusqueda", new Director());
				model.put("listDirectors", dService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listDirectors", dService.findAll());
		}
		
		return "listDirector";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listDirectors", dService.findAllSortAsc());
		model.put("director",new Director());
		model.put("directorbusqueda", new Director());
		
		return "listDirector";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("directorbusqueda") Director director) 
		throws ParseException
	{
		List<Director>listDirectors;
		listDirectors = dService.findByName(director.getNameDirector());
		
		model.put("director", new Director());
		model.put("listDirectors", listDirectors);
		
		return "listDirector";
	}
}
