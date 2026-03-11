package servicio;

import modelo.Pizza;

public class PizzaFactory {

    public static Pizza generarPizza() {

        int r = (int) (Math.random() * 3);

        if (r == 0) {
            return new Pizza(
                    "Pepperoni",
                    "/Pizza_peperoni.jpg",
                    new String[]{"Tomate", "Queso", "Pepperoni"}
            );
        }

        if (r == 1) {
            return new Pizza(
                    "Vegetariana",
                    "/Pizza_vegetariana.jpg",
                    new String[]{"Tomate", "Queso", "Champiñones"}
            );
        }

        return new Pizza(
                "Suprema",
                "/Pizza_suprema.jpg",
                new String[]{"Tomate", "Queso", "Pepperoni", "Champiñones"}
        );
    }
}
