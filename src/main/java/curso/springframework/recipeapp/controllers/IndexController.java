package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class IndexController {

    private RecipeRepository recipeRepository;

    public IndexController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model){
        log.debug("getting index page");
        model.addAttribute("recipes",recipeRepository.findAll());

        return "index";
    }
}
