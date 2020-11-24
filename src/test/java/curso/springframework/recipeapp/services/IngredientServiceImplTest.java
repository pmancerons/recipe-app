package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.converters.IngredientCommandToIngredient;
import curso.springframework.recipeapp.converters.IngredientToIngredientCommand;
import curso.springframework.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import curso.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
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

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;



    @BeforeEach
    void setUp() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
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

    @Test
    public void saveRecipeCommand(){
        IngredientCommand command = new IngredientCommand();
        command.setId(3l);
        command.setRecipeId(2l);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3l);

        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);
        Mockito.when(recipeRepository.save(ArgumentMatchers.any())).thenReturn(savedRecipe);

        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(3l,ingredientCommand.getId());

        Mockito.verify(recipeRepository,Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeRepository,Mockito.times(1)).save(ArgumentMatchers.any());
    }


}