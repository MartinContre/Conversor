package joptionpanes;

import conversor.Converter;
import models.Conversion;
import util.NumberUtils;

/**
 * Pane for length conversion.
 */
public class LengthPane extends BasePane {
    private static final String LENGTH_KEY = "Length";

    /**
     * Creates a new instance of the LengthPane class.
     */
    public LengthPane() {
        super("Conversor de longitud", 400, LENGTH_KEY);
    }

    /**
     * Performs the length conversion.
     *
     * @param conversion The conversion object containing the current unit, target unit, and initial value.
     * @return The formatted conversion result.
     */
    @Override
    protected String getConversion(Conversion conversion) {
        conversion.setConvertedValue(Converter.getConvertedLength(conversion));
        String initialValue = NumberUtils.numberFormat(conversion.getInitialValue());
        return String.format("%s %s es igual a %s %s", initialValue, conversion.getCurrentUnit(), conversion.getConvertedValue(), conversion.getTargetUnit());
    }
}
