-- Tabel users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(500) NOT NULL,
    last_name VARCHAR(500) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100),
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_datetime TIMESTAMP
);

-- Tabel items
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500) UNIQUE NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10, 2) CHECK (price >= 0),
    cost DECIMAL(10, 2) CHECK (cost >= 0),
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100),
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_datetime TIMESTAMP
);

-- Tabel po_h (Purchase Order Headers)
CREATE TABLE po_h (
    id SERIAL PRIMARY KEY,
    datetime TIMESTAMP NOT NULL,
    description VARCHAR(255),
    total_cost DECIMAL(10, 2) CHECK (total_cost >= 0),
    total_price DECIMAL(10, 2) CHECK (total_price >= 0),
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100),
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_datetime TIMESTAMP
);

-- Tabel po_d (Purchase Order Details)
CREATE TABLE po_d (
    id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    item_qty INT CHECK (item_qty > 0),
    item_price DECIMAL(10, 2) CHECK (item_price >= 0),
    item_cost DECIMAL(10, 2) CHECK (item_cost >= 0),
    poh_id INT NOT NULL,
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Foreign key ke items
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    -- Foreign key ke po_h
    FOREIGN KEY (poh_id) REFERENCES po_h(id) ON DELETE CASCADE
);

-- Index untuk mempercepat pencarian
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_item_name ON items(name);
CREATE INDEX idx_pod_poh_id ON po_d(poh_id);