package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.UnitOfMeasureCommand;
import curso.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements  UnitOfMeasureService{
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uOMToUOMCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uOMToUOMCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();

        unitOfMeasureRepository.findAll()
                .forEach(uom ->
                        unitOfMeasureCommands.add(uOMToUOMCommand.convert(uom))
                );

        return unitOfMeasureCommands;
    }
}
