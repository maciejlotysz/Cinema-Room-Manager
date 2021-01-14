package cinema;

public class Room {

    private static int soldTickets = 0;
    private static double percentage = 0;

    private final int FRONT_PRICE = 10;
    private final int BACK_PRICE = 8;

    private int[][] seats;

    private int rowCount;
    private int seatCount;

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
    // FIXME: Private method 'seat_sold(int, int)' is never used
    private void seat_sold(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 1;
    }

    private String seat_toString(int rowNum, int seatNum) {
        return (seats[rowNum - 1][seatNum - 1] == 0)?"-":"S";
    }

    public boolean seat_isSold(int rowNum, int seatNum) {
        return seats[rowNum-1][seatNum-1] != 0;
    }
    public boolean seat_isValid(int rowNum, int seatNum) {
        return (rowNum < 1 || rowNum > rowCount || seatNum < 1 || seatNum > seatCount);
    }

    private void seat_sell(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 1;
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
        settingTicketPrice(rowCount);
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
                // FIXME: Problem String concatenation as argument to 'StringBuilder.append()' call
                // please use: String.format and seat_isSold method with ternary operator instead of seat_toString
                // seat_toString can be removed
                sb.append("  "+seat_toString(rowIdx+1, seatIdx+1));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // TODO: return new Ticket object (price, rows, seat)
    public void buyTicket(int rowNum, int seatNum) {
        seat_sell(rowNum, seatNum);
        settingTicketPrice(rowNum);
        // TODO: countCurrentIncome => calcCurrentIncome
        // TODO: calcCurrentIncome should be called directly in the displayStatistics() method (not here)
        countCurrentIncome(rowNum);
        soldTickets++;
        // FIXME: move percentage to displayStatistics and remove percentage class field
        percentage = (double) soldTickets * 100 / totalSeats;
        // FIXME: remove println
        System.out.println("Ticket price: $" + ticketPrice);
        System.out.println();
    }

    private void countCurrentIncome(int rowNum) {
        // FIXME: code duplication (check settingTicketPrice comments)
        if (totalSeats <= 60) {
            currentIncome += FRONT_PRICE;
        } else if (seat_isFront(rowNum)) {
            currentIncome += FRONT_PRICE;
        } else {
            currentIncome += BACK_PRICE;
        }
        setCurrentIncome(currentIncome);
    }

    // TODO: Change to: `private int getTicketPrice(int rowNum)`
    private void settingTicketPrice(int rowNum) {
        // FIXME: code duplication
        //   if you would inline methods smallCinemaRoomPrices and bigCinemaRoomPrices you will
        //   have the same if else statement like in countCurrentIncome method
        totalSeats = rowCount * seatCount;
        if (totalSeats <= 60) {
            smallCinemaRoomPrices();
        } else {
            bigCinemaRoomPrices(rowNum);
        }
    }

    private void smallCinemaRoomPrices() {
        // TODO: Remove ticketPrice class field
        ticketPrice = FRONT_PRICE;
        // TODO: totalIncome should be calculated in the separate method: calcTotalIncome()
        //    calcTotalIncome should be called directly in the displayStatistics method
        setTotalIncome(totalSeats * ticketPrice);
    }

    private void bigCinemaRoomPrices(int rowNum) {
        int frontSeats = rowCount / 2 * seatCount;
        int backSeats = totalSeats - frontSeats;

        if (rowNum <= rowCount / 2) {
            ticketPrice = FRONT_PRICE;
        } else {
            ticketPrice = BACK_PRICE;
        }
        setTotalIncome(frontSeats * FRONT_PRICE + backSeats * BACK_PRICE);
    }

    public void displayStatistics() {
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                        "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
    }
}