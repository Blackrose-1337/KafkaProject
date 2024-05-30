package blackrose.producer.dto;

import java.util.Objects;

public class AdUserDto {
    public String surname;
    public String name;
    public String phoneNumber;
    public Department department;

    public AdUserDto(){}
    public AdUserDto(String surname, String name, String phoneNumber, Department department) {
        this.surname = surname;
        this.name = name;
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
        if (!Objects.equals(phoneNumber, adUser.phoneNumber)) return false;
        return Objects.equals(department, adUser.department);
    }


    @Override
    public String toString() {
        return "AdUser{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
