package blackrose.producer.service;

import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Service;

@Service
public class TextFieldService {

    public TextField createTextField(String label) {
        TextField textField = new TextField();
        textField.setLabel(label);
        textField.setValue(label);
        textField.setClearButtonVisible(true);
        return textField;
    }
}