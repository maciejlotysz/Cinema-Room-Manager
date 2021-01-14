package cinema;

import java.util.Scanner;

public class Menu {


    public void displayMenu() {
        Scanner sc =  new Scanner(System.in);
        Room room = new Room();

        System.out.println("Enter the number of rows:");
        var rowCount = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        var seatCount = sc.nextInt();
        room.createCinemaRoom(rowCount, seatCount);

        boolean terminate = true;
        while (terminate) {
            displayActions();
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    var roomView = room.getSeatsView();
                    System.out.println(roomView);
                    break;
                case 2:
                    System.out.println("Enter a row number:");
                    int rowNumber = sc.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seatNumber = sc.nextInt();
                    room.buyTicket(rowNumber, seatNumber);
                    break;
                case 3:
                    room.displayStatistics();
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