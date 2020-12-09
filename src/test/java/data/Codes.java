package data;

import lombok.Setter;

@Setter
public class Codes {
    private String code;

    @Override
    public String toString() {
        return code;
    }
}