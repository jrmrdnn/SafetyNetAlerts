package com.safetynet.safetynetalerts.constants;

public class RegexpConstants {

  /**
   * Regular expression pattern that validates a text string.
   *
   * <p>
   * The pattern ensures that the string contains at least one letter or number
   * and may include letters, numbers, spaces, hyphens, periods and apostrophes.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   *   <li><code>^</code> - Start of the string</li>
   *   <li><code>(?=.*[\\p{L}\\p{N}])</code> - Positive lookahead to ensure the string contains at least one letter or number</li>
   *   <li><code>[\\p{L}\\p{N}\\s\\-.']+</code> - Match one or more letters, numbers, spaces, hyphens, periods or apostrophes</li>
   *   <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String TEXT_REGEXP =
    "^(?=.*[\\p{L}\\p{N}])[\\p{L}\\p{N}\\s\\-.']+$";

  /**
   * Regular expression pattern that validates a name string.
   *
   * <p>
   * The pattern ensures that the string contains at least one letter and may
   * include letters, spaces, hyphens, periods and apostrophes.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   *  <li><code>^</code> - Start of the string</li>
   * <li><code>(?=.*[\\p{L}])</code> - Positive lookahead to ensure the string contains at least one letter</li>
   * <li><code>[\\p{L}\\s\\-.']+</code> - Match one or more letters, spaces, hyphens, periods or apostrophes</li>
   * <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String NAME_REGEXP = "^(?=.*[\\p{L}])[\\p{L}\\s\\-.']+$";

  /**
   * Regular expression pattern that validates a number string.
   *
   * <p>
   * The pattern ensures that the string contains only numbers.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   * <li><code>^</code> - Start of the string</li>
   * <li><code>\\d+</code> - Match one or more digits</li>
   * <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String NUMBER_REGEXP = "\\d+";

  /**
   * Regular expression pattern that validates an birthdate string.
   *
   * <p>
   * The pattern ensures that the string contains a birthdate.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   * <li><code>^</code> - Start of the string</li>
   * <li><code>\\d{2}/\\d{2}/\\d{4}</code> - Match a birthdate</li>
   * <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String BIRTHDATE_REGEXP = "^\\d{2}/\\d{2}/\\d{4}$";

  /**
   * Regular expression pattern that validates a phone number string.
   *
   * <p>
   * The pattern ensures that the string contains a phone number.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   * <li><code>^</code> - Start of the string</li>
   * <li><code>\\d{3}-\\d{3}-\\d{4}</code> - Match a phone number</li>
   * <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String ZIP_REGEXP = "^\\d{5}$";

  /**
   * Regular expression pattern that validates a phone number string.
   *
   * <p>
   * The pattern ensures that the string contains a phone number.
   * </p>
   *
   * <p>
   * The pattern breakdown is as follows:
   * <ul>
   * <li><code>^</code> - Start of the string</li>
   * <li><code>\\d{3}-\\d{3}-\\d{4}</code> - Match a phone number</li>
   * <li><code>$</code> - End of the string</li>
   * </ul>
   * </p>
   */
  public static final String PHONE_REGEXP = "^\\d{3}-\\d{3}-\\d{4}$";
}
