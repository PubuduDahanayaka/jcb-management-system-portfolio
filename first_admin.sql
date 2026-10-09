-- SQL script to create the first admin user
-- This script inserts an admin user with auto-generated ID ADM0001

-- Insert the first admin user
INSERT INTO users (
    user_id,
    first_name,
    last_name,
    email,
    contact_number,
    password,
    user_type,
    is_active,
    created_at,
    updated_at
) VALUES (
    'ADM0001',                    -- Auto-generated admin ID
    'System',                     -- First name
    'Administrator',              -- Last name
    'admin@jcbsystem.com',        -- Email
    '1234567890',                 -- Contact number
    'REPLACE_WITH_BCRYPT_HASH', -- Generate a BCrypt hash locally before running.
    'ADMIN',                      -- User type
    true,                         -- Is active
    NOW(),                        -- Created at
    NOW()                         -- Updated at
);

-- Verify the admin user was created
SELECT 
    user_id,
    first_name,
    last_name,
    email,
    user_type,
    is_active,
    created_at
FROM users 
WHERE user_id = 'ADM0001';
