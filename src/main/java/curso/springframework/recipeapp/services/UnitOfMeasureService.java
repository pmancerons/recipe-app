package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAll();
}
