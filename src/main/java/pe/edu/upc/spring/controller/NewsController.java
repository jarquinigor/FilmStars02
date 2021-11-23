package pe.edu.upc.spring.controller;

import java.util.Calendar;
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

import pe.edu.upc.spring.model.News;
import pe.edu.upc.spring.model.NewsComment;
import pe.edu.upc.spring.model.Reaction;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.UserNewsComment;
import pe.edu.upc.spring.service.INewsCommentService;
import pe.edu.upc.spring.service.INewsService;
import pe.edu.upc.spring.service.IReactionService;
import pe.edu.upc.spring.service.IUserNewsCommentService;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/noticia")
public class NewsController {
	@Autowired
	private INewsService nService;

	// OTROS
	@Autowired
	private INewsCommentService ncService;
	@Autowired
	private IUserNewsCommentService uncService;
	@Autowired
	private IReactionService rService;

	// PARA REGISTRAR USER REVIEWS
	@Autowired
	private IUserService uService;
	@Autowired
	private INewsService mService;

	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("news") News objNews, BindingResult binRes, Model model)
			throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listNews", nService.findAllSortIdAsc());
			model.addAttribute("newsbusqueda", new News());
			return "listNews";
		}		
		else {
			boolean flag = nService.save(objNews);
			if (flag)
				return "redirect:/noticia/listar";
			else {
				model.addAttribute("mensaje", "Rochezaso");
				return "redirect:/noticia/irRegistrar";
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<News> objNews = nService.findById(id);
		if (objNews == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/noticia/listar";
		} else {
			model.addAttribute("news", objNews);
			model.addAttribute("newsbusqueda", new News());
			model.addAttribute("listNews", nService.findAllSortIdAsc());
			return "listNews";
		}
	}

	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				nService.delete(id);
				model.put("news", new News());
				model.put("newsbusqueda", new News());
				model.put("listNews", nService.findAllSortIdAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("news", new News());
			model.put("newsbusqueda", new News());
			model.put("listNews", nService.findAllSortIdAsc());
		}
		return "listNews";
	}

	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listNews", nService.findAllSortIdAsc());
		model.put("news", new News());
		model.put("newsbusqueda", new News());

		return "listNews";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute("newsbusqueda") News news) throws ParseException {
		List<News> listNews;
		listNews = nService.findByName(news.getTitleNews());

		model.put("news", new News());
		model.put("listNews", listNews);

		return "listNews";
	}

	@RequestMapping("/verNoticias")
	public String moviesUser(Model model) {
		model.addAttribute("listNewss", nService.findAllSortIdDesc());
		return "newssUser";
	}

	@RequestMapping("/verNoticia")
	public String movieUser(Model model, @RequestParam(value = "id") Integer id,
			@RequestParam(value = "idUser") Integer idUser) {
		// 1. IDENTIFICAR SI ES DUEÑO DEL COMENTARIO (SI ES DUEÑO, NO SE REGISTRA)
		// 2. SI NO ES DUEÑO, SE REGISTRA
		List<NewsComment> listNewsComments = ncService.findByNewsId(id); // LISTA DE COMENTARIOS DE UNA NOTICIA

		for (int i = 0; i < listNewsComments.size(); i++) {

			if (uncService.identifyCommentAuthor(idUser, listNewsComments.get(i).getIdNewsComment()) > 0) { // ES DUEÑO
				// NO SE HACE NADA
			} else { // SI NO ES DUEÑO, SE VERIFICA SI EXISTE PARA REGISTRAR
				if (uncService.identifyCommentNonAuthor(idUser, listNewsComments.get(i).getIdNewsComment()) > 0) { // EXISTE
					// NO SE HACE NADA
				} else {// NO EXISTE --> SE REGISTRA
					UserNewsComment objUserNewsComment = new UserNewsComment();

					objUserNewsComment.setNewsComment(listNewsComments.get(i)); //1

					Optional<Reaction> objReaction = rService.findById(3);
					if (objReaction.isPresent())
						objReaction.ifPresent(o -> objUserNewsComment.setReaction(o));//2

					Optional<Users> objUser = uService.findById(idUser);
					if (objUser.isPresent())
						objUser.ifPresent(o -> objUserNewsComment.setUser(o));//3

					boolean flag1 = uncService.save(objUserNewsComment);
				}
			}
		}

		Optional<News> objNews = nService.findById(id);
		if (objNews.isPresent())
			objNews.ifPresent(o -> model.addAttribute("news", o));

		List<UserNewsComment> listUserNewsComment = uncService.findAllByUserAndNewsId(idUser, id);

		for (int i = 0; i < listUserNewsComment.size(); i++) {

			Calendar current = Calendar.getInstance();

			Calendar comment = Calendar.getInstance();
			comment.setTime(listUserNewsComment.get(i).getNewsComment().getDateNewsComment());

			long miliSeconds = current.getTimeInMillis() - comment.getTimeInMillis();
																						

			long seconds = miliSeconds / 1000;

			if (seconds >= 0 && seconds < 60) {
				listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("un momento");
			}
			if (seconds >= 60 && seconds < 3600) {   
				seconds = seconds / 60;

				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("un minuto");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " minutos");
			}
			if (seconds >= 3600 && seconds < 86400) {
				seconds = seconds / 3600;
				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("una hora");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " horas");
			}
			if (seconds >= 86400 && seconds < 604800) {
				seconds = seconds / 86400;
				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("un día");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " días");
			}
			if (seconds >= 604800 && seconds < 2592000) {
				seconds = seconds / 604800;
				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("una semana");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " semanas");
			}
			if (seconds >= 2592000 && seconds < 31104000) {
				seconds = seconds / 2592000;
				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("un mes");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " meses");
			}
			if (seconds >= 31104000) {
				seconds = seconds / 31104000;
				if (seconds == 1)
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment("un año");
				else
					listUserNewsComment.get(i).getNewsComment().setTextDateNewsComment(seconds + " años");
			}
		}

		model.addAttribute("listUserNewsComments", listUserNewsComment); // LISTA DE COMENTAR
		model.addAttribute("newsComment", new NewsComment());

		return "newsUser";
	}

	
	@RequestMapping("/eliminarComentario")
	public String deleteNewsComment(@RequestParam("idUserNewsComment") Integer idUserNewsComment, 
			@RequestParam("idUserLogged") Integer idUserLogged, RedirectAttributes redirectAttributes) {
		
		UserNewsComment userNewsComment = uncService.findByUNCId(idUserNewsComment);
		
		
		if(userNewsComment.getNewsComment().getUser().getIdUser() == idUserLogged) {
			//PRIMERO ELIMINAMOS TODAS LAS REACCIONES
			uncService.deleteBatch(userNewsComment.getNewsComment().getIdNewsComment());
			
			//LUEGO ELIMINAMOS EL COMENTARIO
			ncService.delete(userNewsComment.getNewsComment().getIdNewsComment());
		}
		
		redirectAttributes.addAttribute("id",userNewsComment.getNewsComment().getNews().getIdNews());//noticia
		redirectAttributes.addAttribute("idUser",idUserLogged);//userlogged
		return "redirect:/noticia/verNoticia";
	}
	
	
	@RequestMapping("/registraComentario")
	public String register(@ModelAttribute("newsComment") NewsComment objNewsComment,
			@RequestParam(value = "idUser") Integer idUser, @RequestParam(value = "idNews") Integer idNews,
			BindingResult binRes, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		if (binRes.hasErrors())
			return "newsUser";
		else {
			// Para registrar un objeto NewsComment
			UserNewsComment objUserNewsComment = new UserNewsComment();

			Optional<Users> objUser = uService.findById(idUser);
			if (objUser.isPresent())
				objUser.ifPresent(o -> objUserNewsComment.setUser(o)); // (1)
			
			//
			Optional<News> objNews = mService.findById(idNews);
			if (objNews.isPresent())
				objNews.ifPresent(o -> objNewsComment.setNews(o));

			if (objUser.isPresent())
				objUser.ifPresent(o -> objNewsComment.setUser(o));

			boolean flag = ncService.save(objNewsComment); // Se registra el objeto NewsComment
			//

			if (flag) {
				// Cargando el model
				objUserNewsComment.setNewsComment(objNewsComment); // (2)

				Optional<Reaction> objReaction = rService.findById(3);
				if (objReaction.isPresent())
					objReaction.ifPresent(o -> objUserNewsComment.setReaction(o)); // (3) ESTÁ LLENO
				boolean flag1 = uncService.save(objUserNewsComment); // REGISTRAMOS OBJETO UserNewsComment

				Optional<News> news = mService.findById(idNews);
				if (news.isPresent())
					news.ifPresent(o -> redirectAttributes.addAttribute("id", o.getIdNews())); // REDIRECT ATTRIBUTE

				Optional<Users> user = uService.findById(idUser);
				if (user.isPresent())
					user.ifPresent(o -> redirectAttributes.addAttribute("idUser", o.getIdUser())); // REDIRECT ATTRIBUTE

				return "redirect:/noticia/verNoticia";
			}

			else {
				model.addAttribute("mensaje", "Rochezaso");
				return "redirect:/usuarioReview/reviews";
			}
		}
	}

	@RequestMapping("/interaccionLike")
	public String interactionLike(@RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idNewsComment") Integer idNewsComment,
			@RequestParam(value = "idNews") Integer idNews,  Model model,
			RedirectAttributes redirectAttributes){
		
		List<UserNewsComment> listUserNewsComment = uncService.findRow(idUser, idNewsComment);
		
		if(listUserNewsComment.get(0).getReaction().getIdReaction()==3) {//SE DA LIKE
			Optional<Reaction> reaction = rService.findById(1);
			if (reaction.isPresent())
				reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));
			
			//LIKES
			Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
			if (newsComment.isPresent())
				newsComment.ifPresent(o -> o.setLikesNewsComment(o.getLikesNewsComment()+1));
			if (newsComment.isPresent())
				newsComment.ifPresent(o -> ncService.save(o));
			//
			
			
			boolean flag1 = uncService.save(listUserNewsComment.get(0));
		}
		else {
			if(listUserNewsComment.get(0).getReaction().getIdReaction()==2) {//SE DA LIKE
				Optional<Reaction> reaction = rService.findById(1);
				if (reaction.isPresent())
					reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));
				
				//LIKES
				Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
				if (newsComment.isPresent())
					newsComment.ifPresent(o -> o.setDislikesNewsComment(o.getDislikesNewsComment()-1));
				if (newsComment.isPresent())
					newsComment.ifPresent(o -> o.setLikesNewsComment(o.getLikesNewsComment()+1));
				if (newsComment.isPresent())
					newsComment.ifPresent(o -> ncService.save(o));
				//
				
				boolean flag1 = uncService.save(listUserNewsComment.get(0));
			}
			else
			{
				if(listUserNewsComment.get(0).getReaction().getIdReaction()==1) {//SE QUITA EL LIKE
					Optional<Reaction> reaction = rService.findById(3);
					if (reaction.isPresent())
						reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));
					
					Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
					if (newsComment.isPresent())
						newsComment.ifPresent(o -> o.setLikesNewsComment(o.getLikesNewsComment()-1));
					if (newsComment.isPresent())
						newsComment.ifPresent(o -> ncService.save(o));
					
					boolean flag1 = uncService.save(listUserNewsComment.get(0));
				}
			}
		}
			
		Optional<News> news = mService.findById(idNews);
		if (news.isPresent())
			news.ifPresent(o -> redirectAttributes.addAttribute("id", o.getIdNews())); // REDIRECT ATTRIBUTE

		Optional<Users> user = uService.findById(idUser);
		if (user.isPresent())
			user.ifPresent(o -> redirectAttributes.addAttribute("idUser", o.getIdUser())); // REDIRECT ATTRIBUTE

		return "redirect:/noticia/verNoticia"; // FALTA CARGAR SUS DATOS
	}

	@RequestMapping("/interaccionDislike")
	public String interactionDislike(@RequestParam(value = "idUser") Integer idUser,
			@RequestParam(value = "idNewsComment") Integer idNewsComment,
			@RequestParam(value = "idNews") Integer idNews,  Model model,
			RedirectAttributes redirectAttributes) {

		List<UserNewsComment> listUserNewsComment = uncService.findRow(idUser, idNewsComment);
		
		if(listUserNewsComment.get(0).getReaction().getIdReaction()==3) {//SE DA DISLIKE
			Optional<Reaction> reaction = rService.findById(2);
			if (reaction.isPresent())
				reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));
			
			//DISLIKE
			Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
			if (newsComment.isPresent())
				newsComment.ifPresent(o -> o.setDislikesNewsComment(o.getDislikesNewsComment()+1));
			if (newsComment.isPresent())
				newsComment.ifPresent(o -> ncService.save(o));
			//
			
			boolean flag1 = uncService.save(listUserNewsComment.get(0));
		}
		else
		{
			if(listUserNewsComment.get(0).getReaction().getIdReaction()==2) {//SE QUITA EL DISLIKE
				Optional<Reaction> reaction = rService.findById(3);
				if (reaction.isPresent())
					reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));
				
				//DISLIKE
				Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
				if (newsComment.isPresent())
					newsComment.ifPresent(o -> o.setDislikesNewsComment(o.getDislikesNewsComment()-1));
				if (newsComment.isPresent())
					newsComment.ifPresent(o -> ncService.save(o));
				//
				
				boolean flag1 = uncService.save(listUserNewsComment.get(0));
			}
			else {
				if(listUserNewsComment.get(0).getReaction().getIdReaction()==1) {
					Optional<Reaction> reaction = rService.findById(2);
					if (reaction.isPresent())
						reaction.ifPresent(o -> listUserNewsComment.get(0).setReaction(o));//SE DA DISLIKE
					
					//DISLIKE
					Optional<NewsComment> newsComment = ncService.findById(idNewsComment);
					if (newsComment.isPresent())
						newsComment.ifPresent(o -> o.setLikesNewsComment(o.getLikesNewsComment()-1));
					if (newsComment.isPresent())
						newsComment.ifPresent(o -> o.setDislikesNewsComment(o.getDislikesNewsComment()+1));
					if (newsComment.isPresent())
						newsComment.ifPresent(o -> ncService.save(o));
					//
					
					boolean flag1 = uncService.save(listUserNewsComment.get(0));
				}
			}
		}	
		
		Optional<News> news = mService.findById(idNews);
		if (news.isPresent())
			news.ifPresent(o -> redirectAttributes.addAttribute("id", o.getIdNews())); // REDIRECT ATTRIBUTE

		Optional<Users> user = uService.findById(idUser);
		if (user.isPresent())
			user.ifPresent(o -> redirectAttributes.addAttribute("idUser", o.getIdUser())); // REDIRECT ATTRIBUTE
		
		return "redirect:/noticia/verNoticia"; // FALTA CARGAR SUS DATOS
	}
}
