package com.isimm.suivi_note.utils.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @NotBlank
    @Size(min = 8 , max = 8)
    private String cin;

    // @Size(min = 8)
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$",
    // message = "password must contains at least 8 character including upercase ,lowercase,numbers and special charater")
    private String password;

    // @Size(min = 8)
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$",
    // message = "password must contains at least 8 character including upercase ,lowercase,numbers and special charater")
    private String confirmPassword;
}
