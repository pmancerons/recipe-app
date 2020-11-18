package curso.springframework.recipeapp.converters;

import curso.springframework.recipeapp.commands.UnitOfMeasureCommand;
import curso.springframework.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null) {
            return null;
        }

        final UnitOfMeasure to =  new UnitOfMeasure();
        to.setId(source.getId());
        to.setDescription(source.getDescription());
        return to;
    }
}
