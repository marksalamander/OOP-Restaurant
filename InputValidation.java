public class InputValidation {
    public static boolean isValid(String input) {
        if (input.equals("q") || input.matches("[1-4]")) {
            return true;
        }
        return false;
    }
}
