package cinema;

import java.util.Scanner;

public class Menu {


    public void displayMenu() {
        Scanner sc =  new Scanner(System.in);
        CinemaRoom cinemaRoom = new CinemaRoom();

        cinemaRoom.createCinemaRoom();
        boolean terminate = true;

        while (terminate) {
            displayActions();
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    cinemaRoom.displayCinemaRoom();
                    break;
                case 2:
                    cinemaRoom.buyTicket();
                    break;
                case 3:
                    cinemaRoom.displayStatistics();
                    break;
                case 0:
                    terminate = false;
                    break;
                default:
                    System.out.println("Invalid action!");
                    break;
            }
        }
    }

    public void displayActions() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }
}