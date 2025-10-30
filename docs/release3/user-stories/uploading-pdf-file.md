## Story Information
**Title:** Upload PDF-file
**Feature:** Backend endpoint for getting the path for a PDF
**Issue number** #258

## Description
**As a** engaged user,
**I want** to see the PDF file for my recipe
**So that** I can read the recipe whenever I need it.

## Acceptance Criteria
- [ ] The "Open PDF" button is implemented so that the PDF opens on a browser.
- [ ] Shows some kind of message if there is no existing PDF for the selected recipe.

## Scenarios
- Scenario: Viewing PDF file for my recipe
- **Given** I am a user
- **And** I want to see the PDF for a recipe I have created
- **When** I click "Open PDF"
- **Then** I should be able to see the PDF file

- Scenario: PDF file does not exist for a recipe
- **Given** I am in Viewrecipe
- **And** The recipe does not have a uploaded PDF file
- **When** I click "Open PDF"
- **Then** I should get a message saying that there is no PDF file connected to the recipe.
