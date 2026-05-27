package esq.playground.api.service;

import esq.playground.api.model.Contact;
import esq.playground.api.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        return contactRepository.findContactsForPhone(phoneNumber.trim());
    }

    public Contact createOrUpdateContact(String phoneNumber, String contactNumber, String displayName) {
        validatePhoneNumber(phoneNumber);
        if (!StringUtils.hasText(contactNumber)) {
            throw new IllegalArgumentException("contactNumber is required");
        }
        return contactRepository.saveOrUpdate(phoneNumber.trim(), contactNumber.trim(), displayName != null ? displayName.trim() : null);
    }

    public java.util.Optional<Contact> findContact(String phoneNumber, String contactNumber) {
        validatePhoneNumber(phoneNumber);
        if (!StringUtils.hasText(contactNumber)) {
            return java.util.Optional.empty();
        }
        return contactRepository.findByPhoneAndContactNumber(phoneNumber.trim(), contactNumber.trim());
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException("phoneNumber is required");
        }
    }
}
