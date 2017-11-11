package pl.com.bottega.hrs.model.commands;

/**
 * Created by user on 07.11.2017.
 */
public class AddDepartmentCommand {

    private String number, name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
