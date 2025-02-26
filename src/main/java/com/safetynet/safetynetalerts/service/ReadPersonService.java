package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import java.util.List;

public interface ReadPersonService {
  /**
   * Get all children at an address.
   * @param address
   * @return
   */
  List<ChildAlertDTO> allChildrenAtAddress(String address);
}
