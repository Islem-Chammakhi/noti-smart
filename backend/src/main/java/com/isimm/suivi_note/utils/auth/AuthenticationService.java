package com.isimm.suivi_note.utils.auth;

import com.isimm.suivi_note.utils.auth.request.AuthenticationRequest;
import com.isimm.suivi_note.utils.auth.request.RefreshRequest;
import com.isimm.suivi_note.utils.auth.request.RegistrationRequest;
import com.isimm.suivi_note.utils.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(final AuthenticationRequest request);
    void register(final RegistrationRequest request);
    AuthenticationResponse refreshToken(final RefreshRequest request);

}
    