package blackrose.producer;

import blackrose.producer.dto.AdUserDto;
import blackrose.producer.dto.Department;
import blackrose.producer.service.TextFieldService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;


@Route("")
public class MainView extends VerticalLayout {

    private final TextFieldService textFieldService;
    private final KafkaTemplate<String, AdUserDto> kafkaTemplate;

    @Value("${company.kafka.topic}")
    private String TOPIC;

    private final TextField surnameTextField;
    private final TextField nameTextField;
    private final ComboBox<Department> deparmentCombobox;
    private final TextField phoneTextField;
    private final TextArea generalTextArea;


    public MainView(TextFieldService textFieldService, KafkaTemplate<String, AdUserDto> kafkaTemplate) {

        this.textFieldService = textFieldService;
        this.kafkaTemplate = kafkaTemplate;

        surnameTextField = textFieldService.createTextField("Surname");
        nameTextField = textFieldService.createTextField("Name");
        deparmentCombobox = new ComboBox<>("Deparment Combobox");
        deparmentCombobox.setItems(Department.values());
        phoneTextField = textFieldService.createTextField("Phone");
        generalTextArea = new TextArea("Information", "Press save button...");
        generalTextArea.setReadOnly(true);
        generalTextArea.setSizeFull();

        add(surnameTextField, nameTextField, deparmentCombobox, phoneTextField, generalTextArea);
        Button button = new Button("Save");
        button.addClickListener(e -> saveButtonClicked());
        add(button);
    }

    private void saveButtonClicked() {
        String surname = surnameTextField.getValue();
        String name = nameTextField.getValue();
        String departmentString = deparmentCombobox.getValue().toString();
        Department department = Department.valueOf(departmentString);
        String phone = phoneTextField.getValue();
        AdUserDto user = new AdUserDto(surname, name, phone, department);

        String text = "Saved: Name = " + name + "\nSurname = " + surname +
                "\nDepartment = " + department + "\nPhone = " + phone ;
        generalTextArea.setValue(text);

        Notification.show(text);

        kafkaTemplate.send(TOPIC, user);
    }
}
