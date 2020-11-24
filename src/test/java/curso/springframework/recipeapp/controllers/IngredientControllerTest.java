package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.commands.RecipeCommand;
import curso.springframework.recipeapp.services.IngredientService;
import curso.springframework.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;

    MockMvc mockMvcIngredientController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService);

        mockMvcIngredientController = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        Mockito.when(recipeService.findCommandById(ArgumentMatchers.any())).thenReturn(recipeCommand);

        mockMvcIngredientController.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredients/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService,Mockito.times(1)).findCommandById(ArgumentMatchers.any());
    }

    @Test
    public void testShowIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        Mockito.when(ingredientService.findByRecipeIdAndId(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
                .thenReturn(ingredientCommand);

        mockMvcIngredientController.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredients/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }


}