public class Deadline extends Task{

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String getDetail(){
        int status = isDone ? 1 : 0;
        return "E" + " | " + status + " | " + this.description + " | " + this.by + "\n";
    }
}
