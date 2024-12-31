INSERT INTO SP_USER (first_name, last_name, email, password) VALUES
                                                                 ('Test', 'User1', 'test1@test.com', '$2a$10$uI1IHoJVNPuMknMRZZwM6uTH0Bx3cq7PoYK4E7nQ7Bh67xW6shrQe'),  -- password: test123
                                                                 ('Test', 'User2', 'test2@test.com', '$2a$10$uI1IHoJVNPuMknMRZZwM6uTH0Bx3cq7PoYK4E7nQ7Bh67xW6shrQe'); -- password: test123

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

-- Insert mock bookings
INSERT INTO SP_BOOKING (start_time, end_time, room_id, user_id) VALUES
                                                                         ('2024-12-31 14:30:00', '2025-01-31 15:30:00', 1, 1),
                                                                         ('2024-12-31 14:30:00', '2025-01-31 15:30:00', 2, 2);
