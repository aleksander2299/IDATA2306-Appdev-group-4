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
('Rooftop Pool'),
('24-hour Gym Access'),
('Breakfast Included'),
('Fjord View'),
('Conference Facilities'),
('EV Charging'),
('Meeting Rooms'),
('In-house Restaurant'),
('Spa Services'),
('Gourmet Dining'),
('Limousine Service'),
('Fitness Center'),
('Bicycle Rental'),
('Business Center'),
('Mini Bar'),
('Private Terrace'),
('Babysitting Service'),
('Complimentary Super Breakfast Buffet'),
('Sky Bar'),
('Eco-Friendly'),
('Rooftop Bar'),
('Whisky Bar'),
('Afternoon Tea'),
('Heritage Building'),
('Art Decor'),
('Waterfront Views'),
('Spa'),
('Library Bar'),
('Fitness Room'),
('Infinity Pool'),
('SkyPark'),
('Luxury Shopping');

-- Source
INSERT INTO source (source_name, location_type, city, country) VALUES
('Andante Hotel', 'City Center', 'Unknown', 'Unknown'),
('Thon Hotel Ålesund', 'Coastal/Fjord View', 'Ålesund', 'Norway'),
('Scandic Parken', 'Park Proximity', 'Unknown', 'Norway'),
('Carlton Tower Hotel', 'Urban', 'Unknown', 'UK'),
('Swissôtel Amsterdam', 'City Center', 'Amsterdam', 'Netherlands'),
('Hotel Homs', 'Historic Center', 'Rome', 'Italy'),
('Radisson Blu Atlantic Hotel, Stavanger', 'City Center with Lake View', 'Stavanger', 'Norway'),
('Clarion Hotel The Hub, Oslo', 'Urban', 'Oslo', 'Norway'),
('Grand Hotel Terminus', 'City Center', 'Bergen', 'Norway'),
('The Thief', 'Waterfront/Urban', 'Oslo', 'Norway'),
('Hotel Bristol, Oslo', 'Historic Center', 'Oslo', 'Norway'),
('Marina Bay Sands', 'Waterfront/City Center', 'Singapore', 'Singapore');

-- Room
-- Assuming source_id 1, 2, 3 correspond to the sources inserted above
INSERT INTO room (source_id, room_name, description, visibility, room_type, image_url) VALUES
(1, 'Single Room', 'City center single room with modern comforts.', true, 'Single', 'https://picsum.photos/800/600'),
(2, 'Superior Room', 'Superior room with fjord view and breakfast included.', true, 'Superior', 'https://picsum.photos/800/600'),
(3, 'Standard Room', 'Comfortable room near the park.', true, 'Standard', 'https://picsum.photos/800/600'),
(3, 'Plus Room', 'Enhanced comfort with extra amenities.', true, 'Plus', 'https://picsum.photos/800/600'),
(3, 'Premium Room', 'Premium stay experience near park.', true, 'Premium', 'https://picsum.photos/800/600'),
(4, 'Deluxe Room', 'Urban deluxe room with luxury features.', true, 'Deluxe', 'https://picsum.photos/800/600'),
(4, 'Executive Suite', 'Spacious suite with executive service.', true, 'Executive Suite', 'https://picsum.photos/800/600'),
(5, 'Classic Room', 'Classic city center stay with amenities.', true, 'Classic', 'https://picsum.photos/800/600'),
(6, 'Standard Room', 'Historic standard room with terrace.', true, 'Standard', 'https://picsum.photos/800/600'),
(6, 'Junior Suite', 'Suite with private terrace.', true, 'Junior Suite', 'https://picsum.photos/800/600'),
(6, 'Suite', 'Spacious suite with all features.', true, 'Suite', 'https://picsum.photos/800/600'),
(7, 'Business Class Room', 'City view with business amenities.', true, 'Business Class', 'https://picsum.photos/800/600'),
(8, 'Moderate Room', 'Eco-friendly moderate room.', true, 'Moderate', 'https://picsum.photos/800/600'),
(8, 'Standard Room', 'Standard urban room with breakfast.', true, 'Standard', 'https://picsum.photos/800/600'),
(9, 'Classic Room', 'Heritage classic room with whisky bar access.', true, 'Classic', 'https://picsum.photos/800/600'),
(10, 'Artist Room', 'Unique artist-themed room with waterfront views.', true, 'Artist', 'https://picsum.photos/800/600'),
(11, 'Deluxe Room', 'Historic deluxe room with fitness access.', true, 'Deluxe', 'https://picsum.photos/800/600'),
(12, 'Club Room', 'Luxury club room with SkyPark access.', true, 'Club Room', 'https://picsum.photos/800/600');

-- Provider
INSERT INTO provider (provider_name) VALUES
('Booking.com'),
('Agoda'),
('momondo'),
('expedia'),
('Airbnb'),
('Kayak'),
('Hotel.com'),
('Hotels.com'),
('Nordic Choice Hotels'),
('TripAdvisor'),
('The Thief Website'),
('Trip.com'),
('Marina Bay Sands Website');

-- RoomProvider
-- Linking rooms (IDs 1-4) with providers (IDs 1-3) and setting prices
-- Assumes room_id and provider_id are auto-generated starting from 1
INSERT INTO room_provider (room_id, provider_id, room_price) VALUES
(1, 1, 150), -- Andante Single @ Booking.com
(1, 2, 200), -- Andante Single @ Agoda
(2, 3, 1200), -- Thon Superior @ momondo
(2, 4, 1400), -- Thon Superior @ expedia
(3, 5, 1100), -- Scandic Standard @ Airbnb
(3, 2, 1500), -- Scandic Standard @ Agoda
(6, 2, 250), -- Carlton Deluxe @ Agoda
(6, 1, 600), -- Carlton Deluxe @ Booking
(8, 6, 180), -- Swissôtel @ Kayak
(8, 1, 240), -- Swissôtel @ Booking
(9, 7, 100), -- Homs Standard @ Hotel.com
(9, 2, 220), -- Homs Standard @ Agoda
(9, 1, 240), -- Homs Standard @ Booking
(12, 8, 950), -- Radisson Standard @ Hotels.com
(12, 1, 990), -- Radisson Standard @ Booking
(13, 9, 1050), -- Clarion Moderate @ Nordic Choice
(13, 4, 1100), -- Clarion Moderate @ Expedia
(15, 10, 1200), -- Terminus Classic @ TripAdvisor
(15, 8, 1250), -- Terminus Classic @ Hotels.com
(16, 11, 2300), -- The Thief @ Their site
(16, 2, 2500), -- The Thief @ Agoda
(17, 1, 1600), -- Bristol Deluxe @ Booking
(17, 4, 1700), -- Bristol Deluxe @ Expedia
(18, 13, 500), -- Marina Club Room @ Hotel Site
(18, 12, 550); -- Marina Club Room @ Trip.com

-- SourceExtraFeatures
-- Linking sources (IDs 1-3) with features
-- Link extra features to hotel sources (samples)
INSERT INTO source_extra_features (source_id, feature) VALUES
(1, 'WiFi'),
(1, 'Rooftop Pool'),
(1, '24-hour Gym Access'),
(2, 'Breakfast Included'),
(2, 'Fjord View'),
(2, 'Conference Facilities'),
(3, 'EV Charging'),
(3, 'Meeting Rooms'),
(3, 'In-house Restaurant'),
(4, 'Spa Services'),
(4, 'Gourmet Dining'),
(4, 'Limousine Service'),
(5, 'Fitness Center'),
(5, 'Bicycle Rental'),
(5, 'Business Center'),
(6, 'Mini Bar'),
(6, 'Private Terrace'),
(6, 'Babysitting Service'),
(7, 'Complimentary Super Breakfast Buffet'),
(7, 'Sky Bar'),
(7, 'EV Charging'),
(8, 'Eco-Friendly'),
(8, 'Rooftop Bar'),
(9, 'Whisky Bar'),
(9, 'Afternoon Tea'),
(9, 'Heritage Building'),
(10, 'Art Decor'),
(10, 'Waterfront Views'),
(10, 'Spa'),
(11, 'Library Bar'),
(11, 'Fitness Room'),
(11, 'Afternoon Tea'),
(12, 'Infinity Pool'),
(12, 'SkyPark'),
(12, 'Luxury Shopping');

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