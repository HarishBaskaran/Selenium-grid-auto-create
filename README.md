# Selenium-grid-auto-create
This snippet is written with JAVA and SELENIDE as base languages.
This is a maven project with testng framework.

This piece of code helps to create the grid and nodes automatically at the start of test run.


The class "genericWrappers" - is responsible to initiate the grid creation.
Use class "specificWrappers" as parent class for each test class, so the driver and other configurations are fetched from the parent.

The grid and nodes will be auto deleted after test run.

Try running testNG.xml and see the magic !!!
