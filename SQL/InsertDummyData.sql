-- Dummy data for UserDB
INSERT INTO UserDB (Username, FirstName, LastName, Email, Password, AvatarLink)
VALUES
    ('user1', 'John', 'Doe', 'john.doe@example.com', 'hashed_password_1', 'https://example.com/avatar1.jpg'),
    ('user2', 'Jane', 'Smith', 'jane.smith@example.com', 'hashed_password_2', 'https://example.com/avatar2.jpg'),
    ('user3', 'Alice', 'Johnson', 'alice.johnson@example.com', 'hashed_password_3', 'https://example.com/avatar3.jpg');

-- Dummy data for FriendDB
INSERT INTO FriendDB (UserID, FriendUserID, Status)
VALUES
    (1, 2, 'Accepted'),
    (1, 3, 'Pending'),
    (2, 3, 'Accepted');

-- Dummy data for GroupDB
INSERT INTO GroupDB (GroupName)
VALUES
    ('Group 1'),
    ('Group 2'),
    ('Group 3');

-- Dummy data for GroupMembershipDB
INSERT INTO GroupMembershipDB (GroupID, GroupUserID, GroupRole)
VALUES
    (1, 1, 'Admin'),
    (1, 2, 'Member'),
    (2, 1, 'Admin'),
    (2, 3, 'Member');

-- Dummy data for ChannelDB
INSERT INTO ChannelDB (ChannelName, ChannelType)
VALUES
    ('Text Channel 1', 'Text'),
    ('Voice Channel 1', 'Voice'),
    ('Text Channel 2', 'Text');

-- Dummy data for ChannelMembershipDB
INSERT INTO ChannelMembershipDB (ChannelID, UserID, ChannelRole)
VALUES
    (1, 1, 'Admin'),
    (1, 2, 'Member'),
    (2, 1, 'Admin'),
    (2, 3, 'Member'),
    (3, 2, 'Admin');
