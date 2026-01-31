package Program;

import model.exceptions.InputException;

import java.util.Scanner;

public class UI {

    public static void clearScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    public static void waitEnter(Scanner sc) {
        System.out.println("\n_________________________________");
        System.out.println("Press ENTER to continue...");
        sc.nextLine(); // Aqui ele trava e fica esperando
    }

    public static Integer validateOption(int option) {
        if(option<1 || option >4) {
            throw new InputException("Invalid option!\n");
        }
        else {
            return option;
        }
    }
}
