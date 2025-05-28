
CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS houses (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(50) NOT NULL,  
   image_name VARCHAR(255),
   description VARCHAR(255) NOT NULL,
   price INT NOT NULL,
	category_id INT,
	price_min INT,
	price_max INT,
	opening_time TIME,
	closing_time TIME,
	holiday VARCHAR(50),
	capacity INT,
   postal_code VARCHAR(50) NOT NULL,
   address VARCHAR(255) NOT NULL,
   phone_number VARCHAR(50) NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    postal_code VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,    
    role_id INT NOT NULL, 
    enabled BOOLEAN NOT NULL,
    stripe_customer_id VARCHAR(255),    
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS verification_tokens (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,        
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) 
);

CREATE TABLE IF NOT EXISTS reservations (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    house_id INT NOT NULL,
    user_id INT NOT NULL,
    checkin_date DATETIME NOT NULL,
    checkout_date DATETIME NOT NULL,
    number_of_people INT NOT NULL,
    amount INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (house_id) REFERENCES houses (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

--kadai2
--    review_updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CREATE TABLE IF NOT EXISTS reviews (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    house_id INT NOT NULL,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    star INT NOT NULL,
    review_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    review_comment TEXT NOT NULL,    
    UNIQUE (house_id, user_id),
    FOREIGN KEY (house_id) REFERENCES houses (id),
    FOREIGN KEY (user_id) REFERENCES users (id) 
);

CREATE TABLE IF NOT EXISTS favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    house_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_favorite_house FOREIGN KEY (house_id) REFERENCES houses(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uc_favorite UNIQUE (house_id, user_id)
);

--nagoyameshi


CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    expiry_date DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


