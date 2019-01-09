package lab3;

import lab2.Function;

public class Main {

    public final static double CHANGE = 2;

    public static void main(String[] args) {
        Function function = (arg) -> Math.pow(Math.E, arg);
        UserInterface userInterface = new UserInterface(function);
        userInterface.draw(700, 900);
    }
}