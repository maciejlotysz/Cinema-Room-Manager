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

    private int rowIdx;
    private int seatIdx;

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
        seats = new int[rowCount][seatCount];
        initSeats();
        settingTicketPrice();
    }

    public void initSeats() {
        for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
            for (int seatIdx = 0; seatIdx < seatCount; seatIdx++) {
                seats[rowIdx][seatIdx] = 0;
            }
        }
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
                var seat = (seats[rowIdx][seatIdx]==0)?"-":"S";
                sb.append(String.format("%3s", seat));
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
        } else if ((rowIdx <= rowCount / 2)) {
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

        if (rowIdx <= rowCount / 2) {
            ticketPrice = FRONT_PRICE;
        } else {
            ticketPrice = BACK_PRICE;
        }
        setTotalIncome(frontSeats * FRONT_PRICE + backSeats * BACK_PRICE);
    }

    private boolean isSoldSeat(int rowIdx,int seatIdx) {
        return seats[rowIdx-1][seatIdx-1] != 0;
    }

    private void sellSeat(int rowIdx,int seaIdx) {
        seats[rowIdx-1][seatIdx-1] = 1;
    }

    private void selectSeat() {
        do {
            System.out.println("Enter a row number:");
            rowIdx = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatIdx = sc.nextInt();
            if (rowIdx < 1 || rowIdx > seats.length || seatIdx < 1 || seatIdx > seats[rowIdx-1].length) {
                System.out.println("Wrong input");
            } else if (isSoldSeat(rowIdx,seatIdx)) {
                System.out.println("That ticket has already been purchased!");
            } else {
                sellSeat(rowIdx,seatIdx);
                return;
            }
        } while (false);
    }

    public void displayStatistics() {
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                        "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
    }
}