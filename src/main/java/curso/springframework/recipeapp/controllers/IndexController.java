package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.repositories.RecipeRepository;
import curso.springframework.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model){
        log.debug("getting index page");
        model.addAttribute("recipes",recipeService.getRecipes());

        return "index";
    }
}
