-- Insert mock users
INSERT INTO SP_USER (first_name, last_name, email, password) VALUES
                                                                 ('John', 'Doe', 'john.doe@example.com', 'password123'),
                                                                 ('Jane', 'Smith', 'jane.smith@example.com', 'password456');

-- Insert mock cards
INSERT INTO SP_CARD (card_number, active, user_id) VALUES
                                                       ('CARD12345', true, 1),
                                                       ('CARD67890', false, 2);

-- Insert mock rooms
INSERT INTO SP_ROOM (name, capacity, available) VALUES
                                                    ('Conference Room A', 10, true),
                                                    ('Meeting Room B', 5, false);

-- Insert mock bookings
INSERT INTO SP_BOOKING (start_time, end_time, active, room_id, user_id) VALUES
                                                                         ('2024-12-20 09:00:00', '2024-12-20 10:00:00', true, 1, 1),
                                                                         ('2024-12-21 11:00:00', '2024-12-21 12:00:00', false, 2, 2);