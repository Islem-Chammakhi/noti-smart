package com.isimm.suivi_note.utils.auth;

import com.isimm.suivi_note.dto.AuthOtpLoginReq;
import com.isimm.suivi_note.utils.auth.request.AuthenticationRequest;
import com.isimm.suivi_note.utils.auth.request.RefreshRequest;
import com.isimm.suivi_note.utils.auth.request.RegistrationRequest;
import com.isimm.suivi_note.utils.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    boolean login(final AuthenticationRequest request);
    AuthenticationResponse loginWithOTP(final AuthOtpLoginReq request);
    void register(final RegistrationRequest request);
    AuthenticationResponse refreshToken(final RefreshRequest request);

}
    