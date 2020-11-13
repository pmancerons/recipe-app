package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
