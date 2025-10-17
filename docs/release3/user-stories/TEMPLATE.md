# User Story Template
- Each user story is a separate file within user-stories
- Each user story follows the following template below

## Story Information
Story information should give basic information of what the user story is about. This includes a title of the user story, the feature it concerns, current status and when it was last updated. A title and feature is needed so that we don't have to read the user story to understand what it is about. The status and last updated is also important so other member can see if the user story is being worked on and if it's up to date with the code.

**Title:** "Very short description of user story"
**Feature:** "Which feature it concerns"  
**Status:** "Draft / Approved / In Progress / Done"
**Last Updated:** "YYYY-MM-DD"

_Example:_
Title: Login with existing account
Feature: Authentication
Status: Done
Last Updated: 2025-10-16

## Description
A description should explain the reason for the user story from the users point of view. It follows a simple format where each line begins with the following syntax: As a, I want, So that. This is to keep the description precise without adding too much detail. 

**As a** "Type of user"
**I want** "Goal or desired action"
**So that** "Benefit or reason"

_Example:_  
As a registered user,  
I want to login to myRecipes,  
So that I can see my personal recipes.

## Acceptance Criteria
List clear, testable conditions that must be met for this story to be complete. This is important as we need to first, understand what needs to be implemented for the user story to be completed. Second, tells the developers which criteria are finished and which needs further work. When these criteria are met, it's then clear to everyone that all implementations are done.

- [ ] Criterion 1  
- [ ] Criterion 2  
- [ ] Criterion 3  

_Example:_  
- [X] The login input fields displayed for a username and password
- [X] Password must be more than 6 characters 
- [X] Username and password must exist in our database
- [X] A login button is needed to authenticate the user

## Scenarios
Scenarios explains how the app behaves in when the user interacts with it. This is different to a description as it explains how our app behaves and not what the user intends to do. A common way to do this is by using the Gherkin format. Gherkin is used by developers to describe how software should behave. It uses the "Given, When, Then, And, But" syntax at the start of each line to describe a scenario. The reason we use Gherkin is because it creates precise scenarios, that are human-readable and consistent. 

_Example:_
Scenario: Successful password reset
  **Given** I am on the "Login" page
  **And** I enter a valid and registered username and password
  **When** I click "Login"
  **Then** I should be directed to "myRecipes" page
