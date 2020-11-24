package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.converters.RecipeCommandToRecipe;
import curso.springframework.recipeapp.converters.RecipeToRecipeCommand;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import javafx.beans.binding.Bindings;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.client.ExpectedCount;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipeById(){
        Recipe recipe = new Recipe();
        recipe.setId(1l);;
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(1l)).thenReturn(optionalRecipe);

        Recipe recipeReturned = recipeService.findById(1l);

        assertNotNull(recipeReturned);
        Mockito.verify(recipeRepository,Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeRepository,Mockito.never()).findAll();
    }

    @Test
    void getRecipes() {

        Recipe r = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(r);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1,recipes.size());

        //verifies that the findAll method is going to be called only one time
        Mockito.verify(recipeRepository,Mockito.times(1)).findAll();
    }

    @Test
    void deleteRecipe(){
        recipeService.deleteById(1l);

        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}