import javax.swing.*;
import java.util.*;
import java.text.*;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Book {
    String title;
    LocalDate dueDate;
    LocalDate returnDate;
    double fine;

    public Book (String title, LocalDate dueDate, LocalDate returnDate, double fine) {
        this.title = title;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }
}

public class LibraryFineCalculator {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("0.00");

        double totalFine = 0;
        boolean running = true;

        while (running) {
            String[] options = {
            "Add Book",
            "Calculate Fines",
            "Show Total Fines",
            "Exit"
        };
        int choice = JOptionPane.showOptionDialog(null, 
            "Select an Oprion", 
            "Library Fine Calculator",
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: // add books
                String title = JOptionPane.showInputDialog(null, "Enter book title:");
                String dueStr =  JOptionPane.showInputDialog(null, "Enter due date (yyyy-MM-dd):");
                String returnStr = JOptionPane.showInputDialog(null, "Enter return date (YYYY-MM-DD):");

            try {
                LocalDate duDate = LocalDate.parse(dueStr, formatter);
                LocalDate retuDate = LocalDate.parse(returnStr, formatter);

                long overdueDays = ChronoUnit.DAYS.between(duDate, retuDate);
                double fine = overdueDays > 0 ? overdueDays * 5 : 0;
                
                books.add(new Book(title, duDate, retuDate, fine));
                JOptionPane.showMessageDialog(null, "Book added!\nFine: " + df.format(fine));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Plaese use YYYY-MM-DD.");
            }
            break;

            case 1: // calculate fine
                totalFine = 0;
                StringBuilder finesMessage = new StringBuilder("Fines for each book:\n");
                for (Book b: books) {
                    finesMessage.append(b.title).append(": ").append(df.format(b.fine)).append("\n");
                    totalFine += b.fine;
                }
                JOptionPane.showMessageDialog(null, finesMessage.toString());
                break;
            
            case 2: // show total fine
                JOptionPane.showMessageDialog(null, "Total Fine: " + df.format(totalFine));
                break;
            
            case 3: // exit
            default:
                running = false;
                JOptionPane.showMessageDialog(null, "Exiting... Thank you!");
                break;
            }
        }
    }
}