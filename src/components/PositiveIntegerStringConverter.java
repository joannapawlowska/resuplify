package components;

import javafx.util.converter.IntegerStringConverter;

public class PositiveIntegerStringConverter extends IntegerStringConverter {

    private final IntegerStringConverter converter = new IntegerStringConverter();

    @Override
    public Integer fromString(String string){
        try{
            Integer integer = converter.fromString(string);
            isPositiveInteger(integer);
            return integer;
        } catch (IllegalArgumentException e){
        }
        return null;
    }

    public void isPositiveInteger(Integer integer) throws IllegalArgumentException{
        if(integer <= 0)
            throw new IllegalArgumentException();
    }
}