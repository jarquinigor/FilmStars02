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

import pe.edu.upc.spring.model.Reaction;
import pe.edu.upc.spring.service.IReactionService;

@Controller
@RequestMapping("/reaccion")
public class ReactionController {
	@Autowired
	private IReactionService rService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("reaction") Reaction objReaction, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "reaction";
		else {
			boolean flag = rService.save(objReaction);
			if(flag)
				return "redirect:/reaccion/listar";
			else {
				model.addAttribute("mensaje", "Ocurrio un rochezaso, LUZ ROJA");
				return "redirect:/reaccion/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Reaction> objReaction = rService.findById(id);
		if (objReaction == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/reaccion/listar";
		}
		else {
			model.addAttribute("reaction", objReaction);
			model.addAttribute("reactionbusqueda", new Reaction());
			model.addAttribute("listReactions",rService.findAll());
			return "listReaction";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				rService.delete(id);
				model.put("reaction",new Reaction()); //importante
				model.put("reactionbusqueda", new Reaction()); //importante
				model.put("listReactions", rService.findAll());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listActors", rService.findAll());
			model.put("actor", new Reaction());
			model.put("actorbusqueda", new Reaction());
		}
		return "listReaction";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listReactions", rService.findAll());
		model.put("reaction",new Reaction());
		model.put("reactionbusqueda", new Reaction()); 
		return "listReaction";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("reactionbusqueda") Reaction reaction) 
		throws ParseException
	{
		List<Reaction>listReactions;
		listReactions = rService.findByName(reaction.getNameReaction());
		
		model.put("reaction", new Reaction());
		model.put("listReactions", listReactions);
		
		return "listReaction";
	}
}
