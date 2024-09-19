-- Создание таблицы Ingredient
CREATE TABLE IF NOT EXISTS Ingredient (
                                          id VARCHAR(4) PRIMARY KEY,
                                          name VARCHAR(25) NOT NULL,
                                          type VARCHAR(10) NOT NULL
);

-- Создание таблицы Taco_Order
CREATE TABLE IF NOT EXISTS Taco_Order (
                                          id SERIAL PRIMARY KEY,
                                          delivery_name VARCHAR(50) NOT NULL,
                                          delivery_street VARCHAR(50) NOT NULL,
                                          delivery_city VARCHAR(50) NOT NULL,
                                          delivery_state VARCHAR(2) NOT NULL,
                                          delivery_zip VARCHAR(10) NOT NULL,
                                          cc_number VARCHAR(16) NOT NULL,
                                          cc_expiration VARCHAR(5) NOT NULL,
                                          cc_cvv VARCHAR(3) NOT NULL,
                                          placed_at TIMESTAMP NOT NULL
);

-- Создание таблицы Taco
CREATE TABLE IF NOT EXISTS Taco (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL,
                                    taco_order BIGINT NOT NULL,
                                    taco_order_key BIGINT NOT NULL,
                                    created_at TIMESTAMP NOT NULL,
                                    CONSTRAINT fk_taco_order FOREIGN KEY (taco_order) REFERENCES Taco_Order(id)
);

-- Создание таблицы Ingredient_Ref
CREATE TABLE IF NOT EXISTS Ingredient_Ref (
                                              ingredient VARCHAR(4) NOT NULL,
                                              taco BIGINT NOT NULL,
                                              taco_key BIGINT NOT NULL,
                                              CONSTRAINT fk_ingredient FOREIGN KEY (ingredient) REFERENCES Ingredient(id),
                                              CONSTRAINT fk_taco FOREIGN KEY (taco) REFERENCES Taco(id)
);
