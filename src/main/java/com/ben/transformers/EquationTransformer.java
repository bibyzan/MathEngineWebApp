package com.ben.transformers;

import com.ben.javacalculator.Equation;

/**
 * Returns a JSON string representing an equation
 * @author Brennan Hoeting
 */
public class EquationTransformer {

    /**
     * Transform an {@code Equation} into a JSON string
     * @param equation The equation to be transformed
     * @return string
     */
    public static String transform(Equation equation) {
        JsonTransformer jsonTransformer = new JsonTransformer();
        return jsonTransformer.render(new JsonableEquation(equation));
    }

    /**
     * Wraps the string representation of an {@code Equation}
     */
    private static class JsonableEquation {

        /**
         * The string representation of an {@code Equation}
         */
        String equation;

        /**
         * Construct a new {@code JsonableEquation}
         * @param equation The equation that will return the string
         */
        public JsonableEquation(Equation equation) {
            this.equation = equation.toString();
        }
    }
}
