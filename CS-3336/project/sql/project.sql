#################################################
#
# 

DROP TABLE IF EXISTS `project`;
CREATE TABLE project (userID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 email VARCHAR(255) NOT NULL,
 birthday DATE NOT NULL,
 userIP VARCHAR(20) NOT NULL,
 loginDate TIMESTAMP NOT NULL);

