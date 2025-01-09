INSERT INTO SP_USER (first_name, last_name, email, password) VALUES
                                                                 ('Test', 'User1', 'test1@test.com', 'test'),
                                                                 ('Test', 'User2', 'test2@test.com', 'test');

-- Insert corresponding cards
INSERT INTO SP_CARD (card_number, user_id) VALUES
                                                       ('49F18AAA', (SELECT id FROM SP_USER WHERE email = 'test1@test.com')),
                                                       ('F515CE10', (SELECT id FROM SP_USER WHERE email = 'test2@test.com'));

-- Insert mock rooms
INSERT INTO SP_ROOM (name) VALUES
                                                    ('Conference Room A'),
                                                    ('Conference Room B'),
                                                    ('Conference Room C'),
                                                    ('Meeting Room A'),
                                                    ('Meeting Room B'),
                                                    ('Meeting Room C');
