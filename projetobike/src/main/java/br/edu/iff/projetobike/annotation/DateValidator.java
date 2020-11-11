package br.edu.iff.projetobike.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.edu.iff.projetobike.model.Reserva;

public class DateValidator implements ConstraintValidator<DateValidation, Reserva> {
    
    @Override
    public boolean isValid(Reserva value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(value.getHoraInicio().compareTo(value.getHoraFim()) <= 0) return false;
        return true;
    }
}
