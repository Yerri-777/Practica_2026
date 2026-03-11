package utilidades;

import modelo.RecetaPizza;

public class RecetasFactory {

    public static RecetaPizza pizzaMargarita() {

        return new RecetaPizza(
                "Margarita",
                new String[]{"Tomate", "Queso"}
        );
    }

    public static RecetaPizza pizzaPepperoni() {

        return new RecetaPizza(
                "Pepperoni",
                new String[]{"Tomate", "Queso", "Pepperoni"}
        );
    }

}
