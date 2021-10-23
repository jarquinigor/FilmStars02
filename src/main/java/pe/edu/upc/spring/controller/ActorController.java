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

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.service.IActorService;

@Controller
@RequestMapping("/actor")
public class ActorController {
	@Autowired
	private IActorService aService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/") //NO LO USAMOS
	public String goActorsListPage(Map<String, Object> model) {
		model.put("listActors", aService.findAll());
		return "listActor"; 
	}
	
	@RequestMapping("/irRegistrar") //NO LO USAMOS
	public String goRegisterPage(Model model) { 
		model.addAttribute("actor", new Actor()); 
		return "actor"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("actor") Actor objActor, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "actor";
		else {
			boolean flag = aService.save(objActor);
			if(flag)
				return "redirect:/actor/listar";
			else {
				model.addAttribute("mensaje", "Rochezaso");
				return "redirect:/actor/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Actor> objActor = aService.findById(id);
		if (objActor == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/actor/listar";
		}
		else {
			model.addAttribute("actor", objActor);
			model.addAttribute("actorbusqueda", new Actor());
			model.addAttribute("listActors",aService.findAllSortAsc());
			return "listActor";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				aService.delete(id);
				model.put("actor",new Actor()); //importante
				model.put("actorbusqueda", new Actor()); //importante
				model.put("listActors", aService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listActors", aService.findAll());
		}
		return "listActor";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listActors", aService.findAllSortAsc());
		model.put("actor",new Actor());
		model.put("actorbusqueda", new Actor()); 
		return "listActor";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("actorbusqueda") Actor actor) 
		throws ParseException
	{
		List<Actor>listActors;
		listActors = aService.findByName(actor.getNameActor());
		
		model.put("actor", new Actor());
		model.put("listActors", listActors);
		
		return "listActor";
	}
}
