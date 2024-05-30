package blackrose.consumer.dto;

public class AdUserDto {
    public String surname;
    public String name;
    public String phoneNumber;
    public Department department;

    public AdUserDto() {
    }

    public AdUserDto(String surname, String name, String phoneNumber, Department department) {
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    @Override
    public String toString() {
        return "AdUserDto: surname=" + surname + ", name=" + name + ", phoneNumber=" + phoneNumber + ", department=" + department;
    }
}
