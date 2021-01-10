package cinema;

import java.util.Scanner;

public class Room {

    private static int soldTickets = 0;
    private static double percentage = 0;

    Scanner sc = new Scanner(System.in);
    private final int FRONT_PRICE = 10;
    private final int BACK_PRICE = 8;

    private String[][] seats;

    private int rowCount;
    private int seatCount;

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
        rowCount = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatCount = sc.nextInt();
        seats = new String[rowCount + 1][seatCount + 1];
        fillSeats();
        settingTicketPrice();
    }

    public void fillSeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (i == 0 && j == 0) {
                    seats[i][j] = " ";
                } else if (i == 0) {
                    seats[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    seats[i][j] = String.valueOf(i);
                } else {
                    seats[i][j] = "S";
                }
            }
        }
    }

    public String getSeatsView() {
        var sb =  new StringBuilder();
        sb.append("Cinema:\n");
        sb.append("   ");
        for (int seatIdx = 1; seatIdx<= seatCount; seatIdx++) {
            sb.append(String.format("%3d",seatIdx));
        }
        sb.append("\n");
        for (int rowIdx = 1; rowIdx<= rowCount; rowIdx++) {
            sb.append(String.format("%2d:",rowIdx));
            for (int seatIdx = 1; seatIdx<= seatCount; seatIdx++) {
                sb.append(String.format("%3s", seats[rowIdx][seatIdx]));
            }
            sb.append("\n");
        }
        return sb.toString();
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
        } else if ((row <= rowCount / 2)) {
            currentIncome += FRONT_PRICE;
        } else {
            currentIncome += BACK_PRICE;
        }
        setCurrentIncome(currentIncome);
    }

    private void settingTicketPrice() {
        totalSeats = rowCount * seatCount;
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
        int frontSeats = rowCount / 2 * seatCount;
        int backSeats = totalSeats - frontSeats;

        if (row <= rowCount / 2) {
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
            if (row < 0 || row > seats.length - 1 || seat < 0 || seat > seats[row].length - 1) {
                System.out.println("Wrong input");
            } else if (seats[row][seat].equalsIgnoreCase("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                flag = false;
            }
        } while (flag);
        seats[row][seat] = "B";
    }

    public void displayStatistics() {
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                        "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
    }
}