package esq.playground.api.dto;

import jakarta.validation.constraints.NotBlank;

public class PhoneRegisterRequest {

    @NotBlank
    private String phoneNumber;
    private String ownerId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
