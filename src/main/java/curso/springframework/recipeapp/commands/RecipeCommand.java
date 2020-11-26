package curso.springframework.recipeapp.commands;

import curso.springframework.recipeapp.domain.Difficulty;
import curso.springframework.recipeapp.domain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Set<IngredientCommand> ingredients  = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

}
