import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TimeFormatter {

    public static String formatDuration(int seconds) {

        if (seconds == 0) return "now";

        List<Integer> timeUnitsInSeconds = List.of(31536000, 86400, 3600, 60, 1);
        List<String> namesOfTimeUnits = List.of("year", "day", "hour", "minute", "second");
        Deque<Integer> dividedResults = new LinkedList<>();
        Deque<String> usedUnits = new LinkedList<>();

        //Divide the given seconds with the required units of time
        for (int i = 0; i < timeUnitsInSeconds.size(); i++) {
            if (seconds / timeUnitsInSeconds.get(i) > 0) {

                //Fill the LinkedLists with the results of the divisions if the current result is greater than zero
                dividedResults.offer(seconds / timeUnitsInSeconds.get(i));
                usedUnits.offer(namesOfTimeUnits.get(i));

                //Extract the counted time units from the remaining seconds
                seconds = seconds - (dividedResults.getLast() * timeUnitsInSeconds.get(i));

            }
        }
        return createSentence(dividedResults, usedUnits);

    }


    private static String createSentence(Deque<Integer> dividedResults, Deque<String> usedUnits) {
        StringBuilder resultString = new StringBuilder();
        int size = dividedResults.size();

        //If the list has only one element, the method returns that element
        if (size == 1) {
            if (dividedResults.peek() > 1) {
                return resultString.append(dividedResults.poll()).append(" ").append(usedUnits.poll()).append("s").toString();
            } else {
                return resultString.append(dividedResults.poll()).append(" ").append(usedUnits.poll()).toString();
            }
        }


        /*If the list has more than one element, the functions iters through the list and
        appends the corresponding element and its name*/
        for (int i = 0; i < size; i++) {

            //The NullPointerException warning can be ignored because the dividedResults can not be 0 (checked in line 7).
            if (dividedResults.peek() > 1) {
                resultString.append(dividedResults.poll()).append(" ").append(usedUnits.poll()).append("s");
                resultString.append(appendSeparator(dividedResults.size()));
            } else if (dividedResults.peek() == 1) {
                resultString.append(dividedResults.poll()).append(" ").append(usedUnits.poll());
                resultString.append(appendSeparator(dividedResults.size()));
            }

        }
        return resultString.toString();
    }


    private static String appendSeparator(int currentSize) {
        String result = "";

        if (currentSize == 1) {
            result = " and ";
        } else if (currentSize > 1) {
            result = ", ";
        }

        return result;
    }



}
