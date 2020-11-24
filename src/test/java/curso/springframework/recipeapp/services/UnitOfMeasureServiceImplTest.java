package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.UnitOfMeasureCommand;
import curso.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import curso.springframework.recipeapp.domain.UnitOfMeasure;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void findAll() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1l);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2l);

        unitOfMeasures.add(uom1);
        unitOfMeasures.add(uom2);

        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAll();

        assertEquals(2,unitOfMeasureCommands.size());

        Mockito.verify(unitOfMeasureRepository,Mockito.times(1)).findAll();

    }
}