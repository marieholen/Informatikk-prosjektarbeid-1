# Tests for Knitspace

This package contains a simple test for the first release of Knitspace. 

When launching the app a simple button is displayed, which can be pressed to create an empty file recipes.txt.

## What is tested

The test simulates clicking on the button and then checks if the file was created. To ensure that there is no file already created the test deletes the old file (if it exists).
