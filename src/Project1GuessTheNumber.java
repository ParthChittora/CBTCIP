
    import java.util.Random;
import javax.swing.JOptionPane;

    public class Project1GuessTheNumber {
        public static void main(String[] args) {
            Random random = new Random();
            int numberToGuess = random.nextInt(100) + 1;
            int numberOfTries = 0;
            int userGuess = 0;
            boolean hasGuessedCorrectly = false;

            while (!hasGuessedCorrectly) {
                String response = JOptionPane.showInputDialog(null,
                        "Guess a number between 1 and 100", "Guess the Number",
                        JOptionPane.QUESTION_MESSAGE);

                try {
                    userGuess = Integer.parseInt(response);
                    numberOfTries++;

                    if (userGuess < numberToGuess) {
                        JOptionPane.showMessageDialog(null, "Too low, try again.");
                    } else if (userGuess > numberToGuess) {
                        JOptionPane.showMessageDialog(null, "Too high, try again.");
                    } else {
                        hasGuessedCorrectly = true;
                        JOptionPane.showMessageDialog(null,
                                "Congratulations! You've guessed the number in " +
                                        numberOfTries + " tries.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

