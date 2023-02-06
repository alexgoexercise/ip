public class Event extends Task {
    protected String from;
    protected String to;

    public Event (String description, int taskID) {
        super(description, taskID);
        if (super.description.contains("/from") && super.description.contains("/to")) {
            int indexOfFrom = super.description.indexOf("/from");
            int indexOfTo = super.description.indexOf("/to");
            if (indexOfFrom < indexOfTo) {
                from = super.description.substring(indexOfFrom + 5, indexOfTo).trim();
                to = super.description.substring(indexOfTo + 3).trim();
                this.description = super.description.substring(0, indexOfFrom);
            } else {
                System.out.println("Hey go check the order of typing(ㆆ_ㆆ)");
            }
        }
        else {
            System.out.println("You didn't type correctly but Keqin still added it to the task...(ㆆ_ㆆ)");
        }
        Task.taskCount += 1;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}