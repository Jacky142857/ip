import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{

    protected String by;
    protected LocalDate ld;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    public Deadline(String description, LocalDate ld) {
        super(description);
        this.ld = ld;
    }

    @Override
    public String toString() {
        if(ld == null) {
            return "[D]" + super.toString() + " (by: " + by + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + ld.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy")) + ")";
        }

    }

    @Override
    public String getDetail(){
        int status = isDone ? 1 : 0;
        return "E" + " | " + status + " | " + this.description + " | " + this.by + "\n";
    }
}
