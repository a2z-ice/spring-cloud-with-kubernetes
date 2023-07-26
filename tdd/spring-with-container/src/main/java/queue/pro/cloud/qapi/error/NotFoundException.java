package queue.pro.cloud.qapi.error;

public class NotFoundException extends RuntimeException {
    public static record Reason(String errorType, String errorDetail){

    }
    private Reason reason;
    public NotFoundException(Reason reason){
        this.reason = reason;
    }

    public Reason getReason(){
        return this.reason;
    }
}
