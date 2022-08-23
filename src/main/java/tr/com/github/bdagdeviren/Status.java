package tr.com.github.bdagdeviren;

public class Status {
    private String message;
    private Integer code;

    public Status(String message,Integer code){
        this.setCode(code);
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
