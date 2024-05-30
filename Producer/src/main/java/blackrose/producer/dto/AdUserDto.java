package blackrose.producer.dto;

import java.util.Objects;

public class AdUserDto {
    public String surname;
    public String name;
    public String email;
    public String phoneNumber;
    public Department department;

    public AdUserDto(){}
    public AdUserDto(String surname, String name, String email, String phoneNumber, Department department) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public String getName() { return name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdUserDto adUser = (AdUserDto) o;

        if (!Objects.equals(surname, adUser.surname)) return false;
        if (!Objects.equals(name, adUser.name)) return false;
        if (!Objects.equals(email, adUser.email)) return false;
        if (!Objects.equals(phoneNumber, adUser.phoneNumber)) return false;
        return Objects.equals(department, adUser.department);
    }

    public boolean equalEmail(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdUserDto adUserDto = (AdUserDto) o;
        return Objects.equals(email, adUserDto.email);
    }

    @Override
    public String toString() {
        return "AdUser{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
