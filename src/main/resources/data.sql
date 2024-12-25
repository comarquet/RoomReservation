-- Insert mock users
INSERT INTO SP_USER (id, first_name, last_name, email, password) VALUES
                                                                            (1, 'John', 'Doe', 'john.doe@example.com', 'password123'),
                                                                            (2, 'Jane', 'Smith', 'jane.smith@example.com', 'password456');

-- Insert mock cards
INSERT INTO SP_CARD (id, card_number, active, user_id) VALUES
                                                               (1, 'CARD12345', true, 1),
                                                               (2, 'CARD67890', false, 2);

-- Insert mock rooms
INSERT INTO SP_ROOM (id, name, capacity, available) VALUES
                                                            (1, 'Conference Room A', 10, true),
                                                            (2, 'Meeting Room B', 5, false);

-- Insert mock bookings
INSERT INTO SP_BOOKING (id, start_time, end_time, active, room, user_id) VALUES
                                                                                 (1, '2024-12-20 09:00:00', '2024-12-20 10:00:00', true, 1, 1),
                                                                                 (2, '2024-12-21 11:00:00', '2024-12-21 12:00:00', false, 2, 2);
