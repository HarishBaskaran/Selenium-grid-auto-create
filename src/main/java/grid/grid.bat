SET location=%CD%
SET selenium=%location%\selenium-server-standalone-3.141.59.jar
SET chromedriver=%location%\chromedriver.exe

java -jar "%selenium%" -role hub -port 4444 -timeout 3000 -browserTimeout 3600 -host localhost