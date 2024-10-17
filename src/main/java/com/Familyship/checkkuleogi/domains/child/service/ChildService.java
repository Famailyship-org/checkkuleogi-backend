package com.Familyship.checkkuleogi.domains.child.service;

import com.Familyship.checkkuleogi.domains.child.dto.CreateChildRequestDTO;
import com.Familyship.checkkuleogi.domains.child.dto.CreateChildResponseDTO;
import com.Familyship.checkkuleogi.domains.child.dto.ReadChildRequestDTO;
import com.Familyship.checkkuleogi.domains.child.dto.ReadChildResponseDTO;

public interface ChildService {

    CreateChildResponseDTO createMBTI(CreateChildRequestDTO childCreateRequestDTO);

    ReadChildResponseDTO readMBTI(ReadChildRequestDTO readChildRequestDTO);
}
