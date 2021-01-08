package cinema;

import java.util.Scanner;

public class CinemaRoom {

    private static int soldTickets = 0;
    private static double percentage = 0;

    Scanner sc = new Scanner(System.in);
    private final int FRONT_PRICE = 10;
    private final int BACK_PRICE = 8;
    private String[][] cinema;
    private int rows;
    private int seats;
    private int row;
    private int seat;
    private int totalSeats;
    private int currentIncome;
    private int totalIncome;
    private int ticketPrice;


    public static int getSoldTickets() {
        return soldTickets;
    }

    public static double getPercentage(){
        return percentage;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }


    public void createCinemaRoom() {
        System.out.println("Enter the number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = sc.nextInt();
        cinema = new String[rows + 1][seats + 1];
        fillSeats();
        settingTicketPrice();
    }

    public void fillSeats() {
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (i == 0 && j == 0) {
                    cinema[i][j] = " ";
                } else if (i == 0) {
                    cinema[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    cinema[i][j] = String.valueOf(i);
                } else {
                    cinema[i][j] = "S";
                }
            }
        }
    }


    public void displayCinemaRoom() {

        System.out.println("Cinema:");
        for (String[] strings : cinema) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void buyTicket() {
        selectSeat();
        settingTicketPrice();
        countCurrentIncome();
        soldTickets++;
        percentage = (double) soldTickets * 100 / totalSeats;
        System.out.println("Ticket price: $" + ticketPrice);
        System.out.println();
    }

    private void countCurrentIncome() {
        if (totalSeats <= 60) {
            currentIncome += FRONT_PRICE;
        } else if ((row <= rows / 2)) {
            currentIncome += FRONT_PRICE;
        } else {
            currentIncome += BACK_PRICE;
        }
        setCurrentIncome(currentIncome);
    }

    private void settingTicketPrice() {
        totalSeats = rows * seats;
        if (totalSeats <= 60) {
            smallCinemaRoomPrices();
        } else {
            bigCinemaRoomPrices();
        }
    }

    private void smallCinemaRoomPrices() {
        ticketPrice = FRONT_PRICE;
        setTotalIncome(totalSeats * ticketPrice);
    }

    private void bigCinemaRoomPrices() {
        int frontSeats = rows / 2 * seats;
        int backSeats = totalSeats - frontSeats;

        if (row <= rows / 2) {
            ticketPrice = FRONT_PRICE;
        } else {
            ticketPrice = BACK_PRICE;
        }
        setTotalIncome(frontSeats * FRONT_PRICE + backSeats * BACK_PRICE);
    }

    private void selectSeat() {
        boolean flag = true;
        do {
            System.out.println("Enter a row number:");
            row = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = sc.nextInt();
            if (row < 0 || row > cinema.length - 1 || seat < 0 || seat > cinema.length - 1) {
                System.out.println("Wrong input");
            } else if (cinema[row][seat].equalsIgnoreCase("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                flag = false;
            }
        } while (flag);
        cinema[row][seat] = "B";
    }

    public void displayStatistics() {
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                        "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
    }
}