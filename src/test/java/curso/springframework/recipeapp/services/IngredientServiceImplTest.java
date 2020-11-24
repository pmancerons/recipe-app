package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.converters.IngredientToIngredientCommand;
import curso.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand,recipeRepository);
    }

    @Test
    void findByRecipeIdAndId() {
        Recipe recipe = new Recipe();
        recipe.setId(1l);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1l);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2l);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3l);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndId(1l,1l);

        assertEquals(1l,ingredientCommand.getId());
        assertEquals(1l,ingredientCommand.getRecipeId());
        Mockito.verify(recipeRepository,Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }
}