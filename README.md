# vote-counting
Vote Counting Application

## Prerequisites:
    -Install Eclipse, and the the JDK 1.8
    
## Setup
	- Import the application into eclipse by using the option File -> Import -> Maven -> Exiting Maven Project
	- Select the project directory and import
	- Right click the project from project explorer and maven build it using option Maven -> Update Maven Project
	
## Run Application
    - Select the Application.java file under package com.app.vote.counting
    - Right click and select the option 'Run as java application' to run the application
    - On startup the application will load the voting options from the text file options.txt placed under the main vote-counting folder
    - The user will be presented with the options, each option will have the prefix for selection e.g A. B. C... Z
    - The user will define the preference for the options by typing the prefixes in a line for example user selected the option with prefix B as 1st, D as 2nd, and A as 3rd. 
      The user will type BDA and hit enter to present the options and take choice from next candidate
    - Once user will type the word tally. The application will end the voting and start calculating the result
    - Application will perform the vote counting and present the winner option on screen.