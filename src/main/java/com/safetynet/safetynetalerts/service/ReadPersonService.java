package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import java.util.List;
import java.util.Set;

public interface ReadPersonService {
  /**
   * Get all children at an address.
   * @param address
   * @return
   */
  List<ChildAlertDTO> allChildrenAtAddress(String address);

  /**
   * Get person info by last name.
   * @param lastName
   * @return
   */
  List<PersonInfoDTO> getPersonInfoByLastName(String lastName);

  /**
   * Get emails by city.
   * @param city
   * @return
   */
  Set<String> getEmailsByCity(String city);
}
