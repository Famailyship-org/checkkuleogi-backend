package com.Familyship.checkkuleogi.domains.child.service;

import com.Familyship.checkkuleogi.domains.child.dto.CreateChildRequestDTO;
import com.Familyship.checkkuleogi.domains.child.dto.CreateChildResponseDTO;

public interface ChildService {

    CreateChildResponseDTO createMBTI(CreateChildRequestDTO childCreateRequestDTO);
}
