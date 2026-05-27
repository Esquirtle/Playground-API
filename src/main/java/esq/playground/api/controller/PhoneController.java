package esq.playground.api.controller;

import esq.playground.api.dto.PhoneRegisterRequest;
import esq.playground.api.model.Phone;
import esq.playground.api.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/register")
    public ResponseEntity<Phone> registerPhone(@Valid @RequestBody PhoneRegisterRequest request) {
        Phone phone = phoneService.registerPhone(request.getPhoneNumber(), request.getOwnerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(phone);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Phone> getPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(phoneService.getPhone(phoneNumber));
    }
}
