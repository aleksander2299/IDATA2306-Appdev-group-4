-- This file was AI-generated to create filler/sample data for the Spring Boot application.
-- All user passwords are 'password' (Bcrypt hash: $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG)


SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE booking;
TRUNCATE TABLE favourite;
TRUNCATE TABLE room_provider;
TRUNCATE TABLE room;
TRUNCATE TABLE source_extra_features;
TRUNCATE TABLE extra_features;
TRUNCATE TABLE source;
TRUNCATE TABLE provider;
TRUNCATE TABLE user;
SET FOREIGN_KEY_CHECKS=1;




-- Users
-- Note: Passwords should be Bcrypt hashed. The example hash is for 'password'.
-- $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG is a hash for 'password'
INSERT INTO user (username, password, role) VALUES
('admin_user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN'),
('normal_user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER'),
('provider_user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'PROVIDER');

-- ExtraFeatures
INSERT INTO extra_features (feature) VALUES
('WiFi'),
('Pool'),
('Gym'),
('Parking'),
('Breakfast Included');

-- Source
INSERT INTO source (source_name, location_type, city, country) VALUES
('Grand Hotel Oslo', 'Hotel', 'Oslo', 'Norway'),
('City Apartments', 'Apartment', 'Bergen', 'Norway'),
('Mountain Lodge', 'Lodge', 'Hemsedal', 'Norway');

-- Room
-- Assuming source_id 1, 2, 3 correspond to the sources inserted above
INSERT INTO room (source_id, room_name, description, visibility, room_type, image_url) VALUES
(1, 'Standard Double Room', 'A comfortable double room with city view.', true, 'Double', 'http://example.com/images/room1.jpg'),
(1, 'Deluxe Suite', 'Spacious suite with a king bed and living area.', true, 'Suite', 'http://example.com/images/room2.jpg'),
(2, 'Two-Bedroom Apartment', 'Modern apartment with two bedrooms and a kitchen.', true, 'Apartment', 'http://example.com/images/room3.jpg'),
(3, 'Rustic Cabin', 'Cozy cabin with mountain views and fireplace.', true, 'Cabin', 'http://example.com/images/room4.jpg');

-- Provider
INSERT INTO provider (provider_name) VALUES
('BookingCom'),
('ExpediaHotels'),
('AirbnbStays');

-- RoomProvider
-- Linking rooms (IDs 1-4) with providers (IDs 1-3) and setting prices
-- Assumes room_id and provider_id are auto-generated starting from 1
INSERT INTO room_provider (room_id, provider_id, room_price) VALUES
(1, 1, 1500), -- Standard Double Room from BookingCom for 1500
(1, 2, 1550), -- Standard Double Room from ExpediaHotels for 1550
(2, 1, 3000), -- Deluxe Suite from BookingCom for 3000
(3, 3, 1200), -- Two-Bedroom Apartment from AirbnbStays for 1200
(4, 1, 2000), -- Rustic Cabin from BookingCom for 2000
(4, 3, 1900); -- Rustic Cabin from AirbnbStays for 1900

-- SourceExtraFeatures
-- Linking sources (IDs 1-3) with features
INSERT INTO source_extra_features (source_id, feature) VALUES
(1, 'WiFi'),
(1, 'Gym'),
(1, 'Breakfast Included'),
(2, 'WiFi'),
(2, 'Parking'),
(3, 'WiFi'),
(3, 'Parking');

-- Favourite
-- Linking users to rooms
-- Assumes room_id are auto-generated starting from 1
INSERT INTO favourite (username, room_id) VALUES
('normal_user', 1),
('normal_user', 3),
('provider_user', 2);

-- Booking
-- Linking room_provider entries to users
-- Assumes room_provider_id are auto-generated starting from 1
-- then this insert needs to reflect that. However, data.sql uses the table schema.
INSERT INTO booking (room_provider, username, check_in_date, check_out_date) VALUES
(1, 'normal_user', '2025-07-01', '2025-07-05'), -- normal_user books RoomProvider ID 1
(3, 'admin_user', '2025-08-10', '2025-08-15');  -- admin_user books RoomProvider ID 3