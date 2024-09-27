Feature: Validating add object get object end point
  Scenario: verify user can add an object
    Given user wants to call "/objects" end point
    And set header type "Content-Type" to "application/json"
    And set request body from the file "add_object.json"
    When user perform post call
    Then verify user status is 200
    And verify id is not empty
    And user stores created booking id into "object.id";
    When user wants to call "/objects/@id" end point
    And user perform get call
    Then verify user status is 200
    And verify response is same as request passed in post call
    And verify response is matching with the ""