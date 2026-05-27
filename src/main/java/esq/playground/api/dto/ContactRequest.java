package esq.playground.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ContactRequest {

    @NotBlank
    private String contactNumber;
    private String displayName;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
