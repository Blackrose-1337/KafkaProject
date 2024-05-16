package blackrose.consumer.dto;

public class AdUserDto {
    public String surname;
    public String name;
    public String email;
    public String phoneNumber;
    public Department department;

    public AdUserDto() {
    }

    public AdUserDto(String surname, String name, String email, String phoneNumber, Department department) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    @Override
    public String toString() {
        return "AdUserDto: surname=" + surname + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", department=" + department;
    }
}
