package esq.playground.api.service;

import esq.playground.api.exception.NotFoundException;
import esq.playground.api.model.Phone;
import esq.playground.api.repository.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Phone registerPhone(String phoneNumber, String ownerId) {
        validatePhoneNumber(phoneNumber);
        return phoneRepository.save(phoneNumber.trim(), StringUtils.hasText(ownerId) ? ownerId.trim() : null);
    }

    public Phone getPhone(String phoneNumber) {
        return phoneRepository.findByPhoneNumber(phoneNumber.trim())
                .orElseThrow(() -> new NotFoundException("Phone number not registered: " + phoneNumber));
    }

    public boolean exists(String phoneNumber) {
        return phoneRepository.existsByPhoneNumber(phoneNumber.trim());
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException("phoneNumber is required");
        }
    }
}
