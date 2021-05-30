Feature: Validate Add place API

  @addplace
  Scenario Outline: Verify if place is bieng successfully added using addplace API
    Given Add place payload with folloing details :
      | name   | language   | address   |
      | <name> | <language> | <address> |
    When user calls "AddPlaceAPI" with "post" http request
    Then APi call is success with code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place id created maps to "<name>" using "GetPlaceAPI"
    Examples:
      | name  | language  | address  |
      | name1 | language1 | address1 |

@deleteplace
  Scenario: Verify if delete place funstionality is fine
    Given delete place payload with following details :
      | name   | language   | address   |
      | <name> | <language> | <address> |
    When user calls "DeletePlaceAPI" with "post" http request
    Then APi call is success with code 200
    And "status" in response body is "OK"