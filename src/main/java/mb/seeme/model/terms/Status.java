package mb.seeme.model.terms;

public enum Status {
    FULL("full"), FREE("free");

    private final String statusDescription;

    Status(String value) {
        statusDescription = value;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
