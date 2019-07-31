SET location=%CD%
SET selenium=%location%\selenium-server-standalone-3.141.59.jar
SET chromedriver=%location%\chromedriver.exe

java -Dwebdriver.chrome.driver="%chromedriver%" -jar "%selenium%" -role node -hub http://localhost:4444/grid/register -browser "browserName=chrome,maxInstances=1,platform=ANY" -maxSession 1 -port %1  -host localhost