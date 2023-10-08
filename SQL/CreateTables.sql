-- User Table
CREATE TABLE UserDB (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(255) UNIQUE NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    AvatarLink VARCHAR(255)
);

-- Friendship Table
CREATE TABLE FriendDB (
    FriendshipID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT,
    FriendUserID INT,
    Status VARCHAR(255),
    FOREIGN KEY (UserID) REFERENCES UserDB(UserID),
    FOREIGN KEY (FriendUserID) REFERENCES UserDB(UserID)
);

-- Group Table
CREATE TABLE GroupDB (
    GroupID INT IDENTITY(1,1) PRIMARY KEY,
    GroupName VARCHAR(255) NOT NULL
);

-- Group Membership Table
CREATE TABLE GroupMembershipDB (
    GroupMembershipID INT IDENTITY(1,1) PRIMARY KEY,
    GroupID INT,
    GroupUserID INT,
    GroupRole VARCHAR(255),
    FOREIGN KEY (GroupID) REFERENCES GroupDB(GroupID),
    FOREIGN KEY (GroupUserID) REFERENCES UserDB(UserID)
);

-- Channel Table
CREATE TABLE ChannelDB (
    ChannelID INT IDENTITY(1,1) PRIMARY KEY,
    ChannelName VARCHAR(255) NOT NULL,
    ChannelType VARCHAR(255)
);

-- Channel Membership Table
CREATE TABLE ChannelMembershipDB (
    MembershipID INT IDENTITY(1,1) PRIMARY KEY,
    ChannelID INT,
    UserID INT,
    ChannelRole VARCHAR(255),
    FOREIGN KEY (ChannelID) REFERENCES ChannelDB(ChannelID),
    FOREIGN KEY (UserID) REFERENCES UserDB(UserID)
);
