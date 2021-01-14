package cinema;

import java.util.Scanner;

public class Room {

    private static int soldTickets = 0;
    private static double percentage = 0;

    Scanner sc = new Scanner(System.in);
    private final int FRONT_PRICE = 10;
    private final int BACK_PRICE = 8;

    private int[][] seats;

    private int rowCount;
    private int seatCount;

    private int rowNumber;

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

    // ----------------------------------------------------------------------------
    // Seat
    // ----------------------------------------------------------------------------

    private void seat_available(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 0;
    }
    private void seat_sold(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 1;
    }

    private String seat_toString(int rowNum, int seatNum) {
        return (seats[rowNum - 1][seatNum - 1] == 0)?"-":"S";
    }

    private boolean seat_isSold(int rowNum, int seatNum) {
        return seats[rowNum-1][seatNum-1] != 0;
    }

    private void seat_sell(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 1;
    }

    private boolean seat_isValid(int rowNum, int seatNum) {
        return (rowNum < 1 || rowNum > rowCount || seatNum < 1 || seatNum > seatCount);
    }

    private boolean seat_isFront(int rowNum) {
        return (rowNum <= rowCount / 2);
    }

    // ----------------------------------------------------------------------------

    public void createCinemaRoom(int rowCount, int seatCount) {
        this.rowCount = rowCount;
        this.seatCount = seatCount;
        seats = new int[rowCount][seatCount];
        for (int row = 1; row <= rowCount; row++) {
            for (int seat = 1; seat <= seatCount; seat++) {
                seat_available(row,seat);
            }
        }
        settingTicketPrice();
    }

    public String getSeatsView() {
        var sb =  new StringBuilder();
        sb.append("Cinema:\n");
        sb.append("   ");
        for (int seatIdx = 0; seatIdx< seatCount; seatIdx++) {
            sb.append(String.format("%3d",seatIdx+1));
        }
        sb.append("\n");
        for (int rowIdx = 0; rowIdx< rowCount; rowIdx++) {
            sb.append(String.format("%2d:",rowIdx+1));
            for (int seatIdx = 0; seatIdx< seatCount; seatIdx++) {
                sb.append("  "+seat_toString(rowIdx+1, seatIdx+1));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void buyTicket(int rowNum, int seatNum) {
        seat_sell(rowNum, seatNum);
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
        } else if (seat_isFront(rowNumber)) {
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

        if (rowNumber <= rowCount / 2) {
            ticketPrice = FRONT_PRICE;
        } else {
            ticketPrice = BACK_PRICE;
        }
        setTotalIncome(frontSeats * FRONT_PRICE + backSeats * BACK_PRICE);
    }

    private void selectSeat(int rowNum, int seatNum) {
            if (seat_isValid(rowNum, seatNum)) {
                System.out.println("Wrong input");
            } else if (seat_isSold(rowNum, seatNum)) {
                System.out.println("That ticket has already been purchased!");
            } else {
                seat_sell(rowNum, seatNum);
            }
    }

    public void displayStatistics() {
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                        "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
    }
}