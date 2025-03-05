package Test.example.Test.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class MessageEntity {
    String message;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public MessageEntity(String message) {
        this.message = message;
        id = null;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
