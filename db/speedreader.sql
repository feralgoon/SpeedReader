
CREATE TABLE Permission (
                PermissionId INT AUTO_INCREMENT NOT NULL,
                PermissionName VARCHAR(50) NOT NULL,
                PRIMARY KEY (PermissionId)
);


CREATE TABLE Source (
                SourceId INT AUTO_INCREMENT NOT NULL,
                SourceName VARCHAR(50) NOT NULL,
                SourceLink VARCHAR(100) NOT NULL,
                PRIMARY KEY (SourceId)
);


CREATE TABLE Post (
                PostId INT AUTO_INCREMENT NOT NULL,
                PostLink VARCHAR(200) NOT NULL,
                PostTitle VARCHAR(200) NOT NULL,
                PostSummary VARCHAR(500) NOT NULL,
                PostDateTime VARCHAR(100) NOT NULL,
                SourceId INT NOT NULL,
                PRIMARY KEY (PostId)
);


CREATE TABLE Patron (
                PatronId INT AUTO_INCREMENT NOT NULL,
                UserName VARCHAR(15) NOT NULL,
                Password VARCHAR(20) NOT NULL,
                Email VARCHAR(50) NOT NULL,
                AccountCreated DATETIME NOT NULL,
                PRIMARY KEY (PatronId)
);


CREATE TABLE Favorite (
                FavoriteId INT AUTO_INCREMENT NOT NULL,
                PatronId INT NOT NULL,
                PostId INT NOT NULL,
                PRIMARY KEY (FavoriteId)
);


CREATE TABLE PermissionLevel (
                PermissionLevelId INT AUTO_INCREMENT NOT NULL,
                PatronId INT NOT NULL,
                PermissionId INT NOT NULL,
                PRIMARY KEY (PermissionLevelId)
);


CREATE TABLE PatronComment (
                PatronCommentId INT AUTO_INCREMENT NOT NULL,
                CommentTime DATETIME NOT NULL,
                CommentContent VARCHAR(500) NOT NULL,
                PostId INT NOT NULL,
                PatronId INT NOT NULL,
                PRIMARY KEY (PatronCommentId)
);


CREATE TABLE Subscription (
                SubscriptionId INT AUTO_INCREMENT NOT NULL,
                PatronId INT NOT NULL,
                SourceId INT NOT NULL,
                PRIMARY KEY (SubscriptionId)
);


ALTER TABLE PermissionLevel ADD CONSTRAINT permission_permissionlevel_fk
FOREIGN KEY (PermissionId)
REFERENCES Permission (PermissionId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Subscription ADD CONSTRAINT source_subscription_fk
FOREIGN KEY (SourceId)
REFERENCES Source (SourceId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Post ADD CONSTRAINT source_post_fk
FOREIGN KEY (SourceId)
REFERENCES Source (SourceId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE PatronComment ADD CONSTRAINT post_patroncomment_fk
FOREIGN KEY (PostId)
REFERENCES Post (PostId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Favorite ADD CONSTRAINT post_favorite_fk
FOREIGN KEY (PostId)
REFERENCES Post (PostId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Subscription ADD CONSTRAINT patron_subscription_fk
FOREIGN KEY (PatronId)
REFERENCES Patron (PatronId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE PatronComment ADD CONSTRAINT patron_patroncomment_fk
FOREIGN KEY (PatronId)
REFERENCES Patron (PatronId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE PermissionLevel ADD CONSTRAINT patron_permissionlevel_fk
FOREIGN KEY (PatronId)
REFERENCES Patron (PatronId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Favorite ADD CONSTRAINT patron_favorite_fk
FOREIGN KEY (PatronId)
REFERENCES Patron (PatronId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;