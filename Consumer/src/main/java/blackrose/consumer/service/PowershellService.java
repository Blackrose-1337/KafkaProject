package blackrose.consumer.service;

import blackrose.consumer.dto.AdUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class PowershellService {

    private static final Logger LOG = LoggerFactory.getLogger(PowershellService.class);

    @Value("${domain.account.mail}")
    private String MAIL;

    @Value("${domain.account.password}")
    private String PASSWORD;

    public String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            System.out.println("PowerShell-Befehl wurde mit Exit-Code " + exitCode + " beendet.");
        } catch (IOException | InterruptedException e) {
            LOG.info("Error Powershell executing command: ", e);
        }
        return output.toString();
    }

    public String createAdUserCommand(AdUserDto adUserDto) {
        String samAccountName = createSamAccountName(adUserDto.name, adUserDto.surname);
        String phoneNumber = adUserDto.phoneNumber.replace(" ", "` ");
        String command = "New-ADUser -Name \"" + adUserDto.name + "` " + adUserDto.surname + "\"" +
                " -GivenName \"" + adUserDto.name + "\"" +
                " -Surname \"" + adUserDto.surname + "\"" +
                " -DisplayName \"" + adUserDto.name + "` " + adUserDto.surname + "\"" +
                " -SamAccountName \"" + samAccountName + "\"" +
                " -Initials \"" + samAccountName + "\"" +
                " -UserPrincipalName \"" + samAccountName + "\"" +
                " -EmailAddress \"" + samAccountName + MAIL + "\"" +
                " -Department \"" + adUserDto.department + "\"" +
                " -OfficePhone \"" + phoneNumber + "\"" +
                " -AccountPassword  (ConvertTo-SecureString \"" + PASSWORD + "\" -AsPlainText -Force) -Enabled $true -PassThru";

        System.out.println("-------------------------------------------COMMAND----------------------------------------------------");
        System.out.println(command);
        System.out.println("-----------------------------------------------------------------------------------------------");
        return command;
    }

    private String createSamAccountName(String name, String surname) {
        String samAccountName = name.toLowerCase().charAt(0) + surname.toLowerCase().substring(0, 2);
        int count = 1;
        while (checkSamAccountName(samAccountName)) {
            samAccountName = name.toLowerCase().charAt(0) + surname.toLowerCase().substring(0, 2) + String.format("%02d", count);
            count++;
        }
        return samAccountName;
    }

    private boolean checkSamAccountName(String samAccountName) {
        String command = "Get-ADUser -Filter {SamAccountName -eq '" + samAccountName + "'}";
        String output = executeCommand(command);
        return !output.isEmpty();
    }
}
