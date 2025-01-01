INSERT INTO SP_USER (first_name, last_name, email, password) VALUES
                                                                 ('Test', 'User1', 'test1@test.com', 'test'),
                                                                 ('Test', 'User2', 'test2@test.com', 'test');

-- Insert corresponding cards
INSERT INTO SP_CARD (card_number, user_id) VALUES
                                                       ('49F18AAA', (SELECT id FROM SP_USER WHERE email = 'test1@test.com')),
                                                       ('F515CE10', (SELECT id FROM SP_USER WHERE email = 'test2@test.com'));

-- Insert mock rooms
INSERT INTO SP_ROOM (name, capacity) VALUES
                                                    ('Conference Room A', 10),
                                                    ('Conference Room B', 10),
                                                    ('Conference Room C', 10),
                                                    ('Meeting Room A', 5),
                                                    ('Meeting Room B', 5),
                                                    ('Meeting Room C', 5);
