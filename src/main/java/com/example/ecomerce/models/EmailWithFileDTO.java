package com.example.ecomerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailWithFileDTO {

    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;

    public String[] getToUser() {
        return toUser;
    }

    public void setToUser(String[] toUser) {
        this.toUser = toUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
