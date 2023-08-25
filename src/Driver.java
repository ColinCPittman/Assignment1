import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        OrderList restaurantOrders = new OrderList();
        int choice;
        do {
            System.out.println(mainMenu);
            choice = getMenuChoice(1, 5, 6, "Choice: ");
            switch (choice) { //enhanced switch to limit scope of the cases, so I can reuse int IDChoice.
                case 1 -> {//new order
                    String[] newItems = new String[3];
                    getItemsInput(newItems);
                    Order ord = new Order(newItems);
                    restaurantOrders.addOrder(ord);
                    System.out.println("New order has been added.");
                }
                case 2 -> {//remove order
                    int IDChoice = getIntAboveZero("Enter order ID [type \"cancel\" to return]: ");
                    if (IDChoice == -1) break;
                    restaurantOrders.removeOrder(IDChoice);
                    System.out.println("The order has been removed");
                }
                case 3 -> {//ready order
                    int IDChoice = getIntAboveZero("Enter order ID [type \"cancel\" to return]: ");
                    if (IDChoice == -1) break;
                    restaurantOrders.readyOrder(IDChoice);
                    System.out.println("The order has been set to \"Ready\".");
                }
                case 4 -> { //print order
                    int IDChoice = getIntAboveZero("Enter order ID [type \"cancel\" to return]: ");
                    if (IDChoice == -1) break;
                    if (restaurantOrders.printOrder(IDChoice).isEmpty()) {
                        System.out.println("No order with such ID.");
                    } else System.out.println(restaurantOrders.printOrder(IDChoice));
                }
                case 5 -> {//print all orders
                    System.out.println(restaurantOrders.printOrders());
                }
                case 6 -> {//exit
                    System.out.println("Shutting down...");
                }
                default -> {
                    break;
                }
            }
        } while (choice != 6);
    }

    /**
     * Used to fill the provided array with menu items in the form of a String which is taken from the user via the Scanner on System.in.
     * Prints out a prompt asking the user to "Enter Item 1", then "Enter Item 2", and so on until the end of the array.
     *
     * @param itemsArray The provided string array which will be filled with user input one at a time.
     */
    public static void getItemsInput(String[] itemsArray) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < itemsArray.length; i++) {
            System.out.print("Enter item " + (i + 1) + ": ");
            itemsArray[i] = sc.nextLine();
        }
    }

    /**
     * For getting an inter input from the user via the Scanner on System.in, intended for use on simple menus in coding assignments
     * where a menu option is selected by entering the corresponding integer on the keyboard. User is repeatedly prompted until a valid input is given that aligns with parameters.
     * @param lowValue Denotes the lowest numbered menu option that doesn't correspond to a menu option that exits the menu.
     * @param highValue Denotes the highest numbered menu option that doesn't correspond to a menu option that exits the menu.
     * @param exitValue Denotes the integer which correspond to the menu option that is intended to exit the user from the menu, must be outside the bounds of lowValue to highValue. Used in prompts to remind user what to enter to exit.
     * @param choicePrompt This is the initial prompt that is printed to ask the user to enter a choice before the scanner waits for input.
     * @return Only the integer that corresponds to anything from lowValue to highValue or exitValue.
     */
    public static int getMenuChoice(int lowValue, int highValue, int exitValue, String choicePrompt) { //method getting an int between starting and ending value (both inclusive).
        Scanner sc = new Scanner(System.in);
        boolean gotError = false;
        int y = exitValue;
        do {
            System.out.print(choicePrompt);
            gotError = false;
            try {
                y = sc.nextInt();
                sc.nextLine();
                if ((!(lowValue <= y && y <= highValue) && y != exitValue)) {
                    gotError = true;
                }
            } catch (Exception err) {
                gotError = true;
                sc.nextLine();
            }
            if (gotError) {
                System.out.println("Invalid input, please enter choice [" + exitValue + " to exit].");
            }
        } while (gotError);
        return y;
    }

    /**
     * Used for getting any non-zero positive integer from the user via the Scanner on System.in. User is continually prompted until a valid input is given, with the option typing "cancel" if they wish to return without providing a value.
     * @param choicePrompt This is the initial prompt that is printed to ask the user to enter a choice before the scanner waits for input.
     * @return Either a non-zero positive provided by the user or -1 if the user chose to cancel without providing the integer.
     */
    public static int getIntAboveZero(String choicePrompt) {
        Scanner scanner = new Scanner(System.in);
        int value;

        while (true) {
            System.out.print(choicePrompt);
            String input = scanner.nextLine();

            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Please enter a positive non-zero value or \"cancel\" to return.");
                    continue;
                }
                break;
            } catch (Exception err) {
                if ("cancel".equalsIgnoreCase(input)) {
                    return -1;
                }
                System.out.println("Invalid input. Please enter a number or \"cancel\" to return.");
            }
        }

        return value;
    }

    public static String mainMenu = """
                                
                Please select a menu option:                
                1-	Create order
                2-	Delete order
                3-	Ready order
                4-	Print order
                5-	Print all orders
                6-	Exit""";
    }
