# User Story Template
> Follow the INVEST principle: Independent, Negotiable, Valuable, Estimable, Small, Testable.
> Each user story is a separate file within user-stories that follows the following template below

---

## Story Information
**Title:** <short, descriptive title>  
**Feature:** <if applicable>  
**Status:** Draft / Approved / In Progress / Done  
**Last Updated:** YYYY-MM-DD  

---

## Description
**As a** <type of user / persona>  
**I want** <some goal or desired action>  
**So that** <the benefit or reason>

_Example:_  
> As a registered user,  
> I want to reset my password via email,  
> So that I can regain access if I forget it.

---

## Acceptance Criteria
List clear, testable conditions that must be met for this story to be complete.

- [ ] Criterion 1  
- [ ] Criterion 2  
- [ ] Criterion 3  

_Example:_  
- [ ] The reset email must include a secure, single-use token  
- [ ] The link expires after 15 minutes  
- [ ] Password must meet security requirements (min 8 chars, etc.)

---

## Scenarios (Gherkin format)
Use Gherkin syntax for detailed behavior that QA or automated tests can follow. Gherkin is used by developers to describe
how software should behave. It uses the "Given, When, Then, And, But" syntax at the start of each line to describe a scenario

```gherkin
Scenario: Successful password reset
  Given I am on the "Forgot password" page
  And I enter a valid registered email
  When I click "Send reset link"
  Then I should receive a password reset email
  And the email should contain a valid reset link
