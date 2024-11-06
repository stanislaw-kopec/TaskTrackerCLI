public enum Status {
    TODO("Todo"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Status fromString(String statusString) {
        for(Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(statusString) || status.name().equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + statusString);
    }
}
