import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static boolean isExit(String input) {
        return input.startsWith("bye");
    }

    public static boolean hasKeyword(String[] nextLineArr, String keyword){
        for(int i = 0; i < nextLineArr.length; i++) {
            if(nextLineArr[i].equals(keyword)) {
                return true;
            }
        }
        return false;
    }
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    public static boolean isDate(String possibleDate) {
        try{
            LocalDate date = LocalDate.parse(possibleDate);
            return true;
        } catch(DateTimeParseException e) {
            return false;
        }
    }
    public static Task parseFileLine(String nextLine) {
        String[] strArr = nextLine.split(" \\| ");
        String[] subStrArr;
        String command = strArr[0];
        boolean isDone = strArr[1].equals("1") ? true : false;
        switch (command) {
            case "T":
                return new Todo(strArr[2], isDone);
            case "D":
                if(isDate(strArr[3])){
                    LocalDate ld = LocalDate.parse(strArr[3]);
                    return new Deadline(strArr[2], ld, isDone);
                }
                return new Deadline(strArr[2], strArr[3], isDone);
            case "E":
                if(isDate(strArr[3])){
                    LocalDate ld = LocalDate.parse(strArr[3]);
                    return new Event(strArr[2], ld, isDone);
                }
                return new Event(strArr[2], strArr[3], isDone);
            default:
                return null;
        }
    }

    public static String parseInputLine(String input, TaskList taskList) throws DukeException {
        String[] strArr = input.split(" ");
        String command = strArr[0];
        if(!(command.equals("bye") || command.equals("list") || command.equals("mark") || command.equals("todo")
                || command.equals("deadline") || command.equals("event") || command.equals("delete"))) {
            throw new DukeInvalidCommandException();
        }
        if (command.equals("list")) {
            StringBuilder message = new StringBuilder("Here are the tasks in your list:");
            for (int i = 1; i <= taskList.size(); i++) {
                int index = i - 1;
                message.append("\n" + i + ". " + taskList.get(index).toString());
            }
            return message.toString();
        }
        if (command.equals("mark")) {
            int index = Integer.parseInt(strArr[1]) - 1;
            Task temp;
            try{
                temp = taskList.get(index);
            } catch(IndexOutOfBoundsException e) {
                throw new DukeInvalidIndexException();
            }
            temp.isDone = true;
            return "Nice! I've marked this task as done:\n" + temp.toString();

        }

        if (command.equals("todo")) {
            String title;
            try{
                title = input.substring("todo".length() + 1).trim();
            }
            catch (StringIndexOutOfBoundsException e){
                throw new DukeEmptyArgumentException();
            }
            Task task = new Todo(title);
            taskList.insert(task);
            return "Got it. I've added this task:" + "\n" +
            "   " + task.toString() + "\n" + "Now you have " + taskList.size() + " tasks in the list.";
        }

        if (command.equals("deadline")) {
            if (strArr.length == 1) throw new DukeEmptyArgumentException();
            if (hasKeyword(strArr, "/by")) {
                String title;
                String[] splitArr = input.split("/by", 2);
                try{
                    title = splitArr[0].substring("deadline".length() + 1).trim();
                }
                catch (StringIndexOutOfBoundsException e){
                    throw new DukeEmptyArgumentException();
                }
                String time = splitArr[1].trim();
                Task task;
                if(isDate(time)) {
                    LocalDate ld = LocalDate.parse(time);
                    task = new Deadline(title, ld);
                } else {
                    task = new Deadline(title, time);
                }
                taskList.insert(task);
                return "Got it. I've added this task:" + "\n" + "   " + task.toString() + "\n" + "Now you have " + taskList.size() + " tasks in the list.";
            }
            else {
                throw new DukeMissingArgumentException("/by");
            }
        }
        if (command.equals("event")) {
            if (strArr.length == 1) throw new DukeEmptyArgumentException();
            if (hasKeyword(strArr, "/at")) {
                String title;
                String[] splitArr = input.split("/at", 2);

                try{
                    title = splitArr[0].substring("event".length() + 1).trim();
                }
                catch (StringIndexOutOfBoundsException e){
                    throw new DukeEmptyArgumentException();
                }
                String time = splitArr[1].trim();
                Task task;
                if(isDate(time)) {
                    LocalDate ld = LocalDate.parse(time);
                    task = new Event(title, ld);
                } else {
                    task = new Event(title, time);
                }
                taskList.insert(task);
                return "Got it. I've added this task:" + "\n" +
                "   " + task.toString() + "\n" + "Now you have " + taskList.size() + "tasks in the list.";
            }
            else {
                throw new DukeMissingArgumentException("/at");
            }
        }
        if (command.equals("delete")){
            int index = Integer.parseInt(strArr[1]) - 1;
            Task task;
            try{
                task = taskList.get(index);
                taskList.delete(index);
            } catch(IndexOutOfBoundsException e) {
                throw new DukeInvalidIndexException();
            }
            return "Noted. I've removed this task:" + "\n" + task.toString() + "\n"+
            "Now you have " + taskList.size() + " tasks in the list.";
        }
        return null;
    }
}
