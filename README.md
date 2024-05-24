# Selenium Test Assignment

This is the repository for the final Selenium Assignment of the Software Quality and Testing subject at ELTE university. I chose to test the Stardew Valley Wiki, searching pages and testing basic user interaction on a browser.

## Before running the tests
Check if you have the dependencies on your computer. Also, create an account in Stardew Valley's Wiki and add a `.env` file inside the `tests/` folder, with your credentials, in this format:
```
USERNAME=<your_personal_username>
PASSWORD=<your_personal_password>
```

This will guarantee you can run the login and logout tests.

## How to run the tests
1. Clone this repository with `git clone`.
2. Run `docker-compose up`. Make sure you have the dependencies (docker, docker-compose...) for this command.
3. Now you can watch the noVNC server (that will simulate the browser interaction): go to `http://localhost:8081/`.
4. In another tab, run also `docker exec -it <name_of_your_ubuntu_container> bash`.
5. Change the folder with `cd tests`.
6. Run tests with `gradle clean && gradle test`. Repeat this as many times as you wish (make sure again that you have the dependencies, such as the gradle itself, for the command).
7. You can check the reports by opening the `tests/build/reports/tests/test/index.html` file.
