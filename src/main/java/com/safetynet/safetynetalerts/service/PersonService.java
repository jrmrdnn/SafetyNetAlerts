package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildAlertDTO.HouseholdMember;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService implements ReadPersonService {

  private final ReadPersonRepository readPersonRepository;
  private final ReadMedicalRecordRepository readMedicalRecordRepository;
  private final CalculateAgeServiceInterface calculateAgeService;

  @Override
  public List<ChildAlertDTO> allChildrenAtAddress(String address) {
    List<ChildAlertDTO> children = new ArrayList<>();

    List<Person> personsAtAddress = readPersonRepository.findPersonsAtAddress(
      address
    );

    for (Person person : personsAtAddress) {
      Optional<MedicalRecord> medicalRecord =
        readMedicalRecordRepository.findByFirstNameAndLastName(person);

      if (medicalRecord.isPresent()) {
        int age = calculateAgeService.calculate(
          medicalRecord.get().getBirthdate()
        );
        if (calculateAgeService.isChild(age)) {
          ChildAlertDTO childDTO = ChildAlertDTO.createChildAlertDTO(
            person,
            age
          );
          children.add(allHouseholdMembers(personsAtAddress, person, childDTO));
        }
      }
    }
    return children;
  }

  /**
   * Create a ChildAlertDTO with all household members.
   * @param personsAtAddress
   * @param person
   * @param child
   * @return ChildAlertDTO
   */
  private ChildAlertDTO allHouseholdMembers(
    List<Person> personsAtAddress,
    Person person,
    ChildAlertDTO child
  ) {
    List<HouseholdMember> householdMembers = personsAtAddress
      .stream()
      .filter(p ->
        !(p.getFirstName().equals(person.getFirstName()) &&
          p.getLastName().equals(person.getLastName()))
      )
      .map(p -> {
        HouseholdMember member = new ChildAlertDTO.HouseholdMember();
        member.setFirstName(p.getFirstName());
        member.setLastName(p.getLastName());
        return member;
      })
      .collect(Collectors.toList());

    child.setHouseholdMembers(householdMembers);

    return child;
  }
}
